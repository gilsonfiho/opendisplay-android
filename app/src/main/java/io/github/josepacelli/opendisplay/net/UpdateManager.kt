package io.github.josepacelli.opendisplay.net

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.content.FileProvider
import io.github.josepacelli.opendisplay.BuildConfig
import io.github.josepacelli.opendisplay.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

sealed class UpdateState {
    object Idle : UpdateState()
    object Checking : UpdateState()
    data class UpdateAvailable(
        val version: String,
        val downloadUrl: String,
        val releaseNotes: String?,
    ) : UpdateState()
    data class Error(val message: String) : UpdateState()
}

/**
 * Android-framework side of auto-update: network call, APK download, install trigger.
 * Pure logic (version comparison, JSON parsing) lives in [UpdateChecker] and is unit-tested.
 * This file is excluded from the coverage gate (see `coverageExcludes` in build.gradle.kts).
 */
object UpdateManager {
    private const val GITHUB_API_URL = "https://api.github.com/repos/josepacelli/opendisplay-android/releases/latest"
    private const val UPDATE_PREFS = "opendisplay_updates"
    private const val KEY_LAST_CHECK = "lastCheckMs"
    private const val CHECK_INTERVAL_MS = 24 * 60 * 60 * 1000L

    private var currentDownloadId: Long = -1

    fun hasCheckedRecently(context: Context): Boolean {
        val prefs = context.getSharedPreferences(UPDATE_PREFS, Context.MODE_PRIVATE)
        return System.currentTimeMillis() - prefs.getLong(KEY_LAST_CHECK, 0L) < CHECK_INTERVAL_MS
    }

    fun markChecked(context: Context) {
        context.getSharedPreferences(UPDATE_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putLong(KEY_LAST_CHECK, System.currentTimeMillis())
            .apply()
    }

    suspend fun checkForUpdate(): UpdateState = withContext(Dispatchers.IO) {
        try {
            val connection = (URL(GITHUB_API_URL).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("Accept", "application/vnd.github.v3+json")
                connectTimeout = 10_000
                readTimeout = 10_000
            }
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return@withContext UpdateState.Error("GitHub connection error: ${connection.responseCode}")
            }
            val body = connection.inputStream.bufferedReader().use { it.readText() }
            val info = UpdateChecker.parseLatestRelease(body)
                ?: return@withContext UpdateState.Error("No APK found in the latest release.")
            if (!UpdateChecker.isNewerVersion(info.version, BuildConfig.VERSION_NAME)) {
                return@withContext UpdateState.Idle
            }
            UpdateState.UpdateAvailable(info.version, info.downloadUrl, info.releaseNotes)
        } catch (e: Exception) {
            Log.warn("Update check failed", e)
            UpdateState.Error("Failed to check for updates: ${e.message}")
        }
    }

    fun downloadApk(context: Context, downloadUrl: String, version: String) {
        File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "OpenDisplay-update.apk").delete()

        val request = DownloadManager.Request(Uri.parse(downloadUrl))
            .setTitle("Downloading OpenDisplay $version")
            .setDescription("Downloading update...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "OpenDisplay-update.apk")
            .setMimeType("application/vnd.android.package-archive")

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        currentDownloadId = downloadManager.enqueue(request)

        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == currentDownloadId) {
                    ctx?.unregisterReceiver(this)
                    installApk(context)
                }
            }
        }
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(onComplete, filter, Context.RECEIVER_EXPORTED)
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            context.registerReceiver(onComplete, filter)
        }
    }

    private fun installApk(context: Context) {
        val apkFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "OpenDisplay-update.apk")
        if (!apkFile.exists()) {
            Log.warn("Downloaded APK not found")
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !context.packageManager.canRequestPackageInstalls()) {
            context.startActivity(
                Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                },
            )
            return
        }
        val apkUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", apkFile)
        context.startActivity(
            Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            },
        )
    }
}