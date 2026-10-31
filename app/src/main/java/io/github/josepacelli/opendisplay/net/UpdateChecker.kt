package io.github.josepacelli.opendisplay.net

import org.json.JSONException
import org.json.JSONObject

/** Parsed GitHub release data: version, APK URL, and optional release notes. */
data class UpdateInfo(
    val version: String,
    val downloadUrl: String,
    /** Cleaned release notes from the GitHub release body, or `null` if absent/blank. */
    val releaseNotes: String?,
)

/**
 * Pure logic for auto-update — no Android framework dependency, so it runs on the JVM
 * and must hit the 95% coverage gate (see CONTRIBUTING.md). Network / download / install
 * live in [UpdateManager].
 */
object UpdateChecker {
    private const val APK_PREFIX = "OpenDisplay-android-"
    private const val APK_SUFFIX = ".apk"
    /** Cap the rendered changelog — full release notes can be long, and a 3000-char dialog
     * is already more than anyone reads. */
    private const val MAX_RELEASE_NOTES_LENGTH = 3000

    /**
     * Compares dotted versions like "v1.2.3" or "1.2.3". Non-numeric parts are ignored.
     * @return true if [latest] is newer than [current].
     */
    fun isNewerVersion(latest: String, current: String): Boolean {
        val latestParts = latest.removePrefix("v").split(".").mapNotNull { it.toIntOrNull() }
        val currentParts = current.removePrefix("v").split(".").mapNotNull { it.toIntOrNull() }
        val maxLength = maxOf(latestParts.size, currentParts.size)
        for (i in 0 until maxLength) {
            val l = latestParts.getOrElse(i) { 0 }
            val c = currentParts.getOrElse(i) { 0 }
            if (l > c) return true
            if (l < c) return false
        }
        return false
    }

    /**
     * Parses the JSON body returned by GitHub's `/releases/latest` endpoint.
     * @return [UpdateInfo] if a matching APK asset exists; `null` if the JSON is malformed,
     * required fields are missing, or no APK asset matches.
     */
    fun parseLatestRelease(jsonBody: String): UpdateInfo? {
        return try {
            val json = JSONObject(jsonBody)
            val tagName = json.optString("tag_name").takeIf { it.isNotEmpty() } ?: return null
            val assets = json.optJSONArray("assets") ?: return null
            val releaseNotes = json.optString("body")
                .takeIf { it.isNotBlank() }
                ?.let { sanitizeReleaseNotes(it) }
            for (i in 0 until assets.length()) {
                val asset = assets.optJSONObject(i) ?: continue
                val name = asset.optString("name")
                if (name.startsWith(APK_PREFIX) && name.endsWith(APK_SUFFIX)) {
                    val url = asset.optString("browser_download_url").takeIf { it.isNotEmpty() } ?: continue
                    return UpdateInfo(tagName, url, releaseNotes)
                }
            }
            null
        } catch (e: JSONException) {
            null
        }
    }

    /**
     * Strips the most jarring Markdown syntax so the raw `body` reads acceptably without
     * pulling in a full Markdown renderer, then truncates to [MAX_RELEASE_NOTES_LENGTH].
     * Headers (`#`/`##`), bold (`**`), inline code (`` ` ``), and `[text](url)` links are
     * handled — the release notes for this project use only these.
     */
    private fun sanitizeReleaseNotes(raw: String): String {
        val cleaned = raw
            .replace(Regex("^#+\\s+", RegexOption.MULTILINE), "")
            .replace("**", "")
            .replace("`", "")
            .replace(Regex("\\[([^]]+)]\\([^)]+\\)"), "$1")
            .trim()
        return if (cleaned.length > MAX_RELEASE_NOTES_LENGTH) {
            cleaned.substring(0, MAX_RELEASE_NOTES_LENGTH) + "…"
        } else {
            cleaned
        }
    }
    
}