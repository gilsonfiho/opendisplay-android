package io.github.josepacelli.opendisplay.net

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdateCheckerTest {

    // ... (các test cũ của bạn giữ nguyên ở đây) ...

    // ---- Dán 3 test mới vào đây ----

    @Test
    fun `isNewerVersion handles empty or non-numeric version strings`() {
        assertFalse(UpdateChecker.isNewerVersion("v", "v"))
        assertTrue(UpdateChecker.isNewerVersion("1.0", "v"))
        assertFalse(UpdateChecker.isNewerVersion("v", "1.0"))
        assertFalse(UpdateChecker.isNewerVersion("a", "b"))
    }

    @Test
    fun `parseLatestRelease skips assets with APK prefix but non-APK suffix`() {
        val json = """
            {
              "tag_name": "v0.0.57",
              "assets": [
                {"name": "OpenDisplay-android-v0.0.57.zip", "browser_download_url": "https://example.com/src.zip"}
              ]
            }
        """.trimIndent()
        assertNull(UpdateChecker.parseLatestRelease(json))
    }

    @Test
    fun `parseLatestRelease skips null entries in assets array`() {
        val json = """
            {
              "tag_name": "v0.0.57",
              "assets": [
                null,
                {"name": "OpenDisplay-android-v0.0.57.apk", "browser_download_url": "https://example.com/app.apk"}
              ]
            }
        """.trimIndent()
        val info = UpdateChecker.parseLatestRelease(json)
        assertNotNull(info)
        assertEquals("https://example.com/app.apk", info!!.downloadUrl)
    }
} // <-- Dấu } đóng class