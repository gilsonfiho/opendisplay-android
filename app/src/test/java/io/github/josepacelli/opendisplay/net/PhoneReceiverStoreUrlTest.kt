package io.github.josepacelli.opendisplay.net

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/** SCR-003: `store` comes from an unauthenticated peer — must collapse to null
 * unless it's an https URL on an allowed host, never passed through as-is. */
class PhoneReceiverStoreUrlTest {

    @Test
    fun `accepts an https URL on an allowed host`() {
        val url = "https://github.com/josepacelli/opendisplay-android/releases/latest"
        assertEquals(url, PhoneReceiver.sanitizedStoreUrl(url))
    }

    @Test
    fun `rejects a non-https scheme`() {
        assertNull(PhoneReceiver.sanitizedStoreUrl("http://github.com/x"))
        assertNull(PhoneReceiver.sanitizedStoreUrl("javascript:alert(1)"))
    }

    @Test
    fun `rejects a host outside the allowlist`() {
        assertNull(PhoneReceiver.sanitizedStoreUrl("https://evil.example/x"))
    }

    @Test
    fun `rejects github URLs outside this repo`() {
        assertNull(PhoneReceiver.sanitizedStoreUrl("https://github.com/attacker/repo/releases/x.apk"))
        assertNull(PhoneReceiver.sanitizedStoreUrl("https://github.com/josepacelli/opendisplay-android-fake/x"))
        assertNull(
            PhoneReceiver.sanitizedStoreUrl("https://github.com/josepacelli/opendisplay-android/../../attacker/repo"),
        )
    }

    @Test
    fun `accepts this app's Play Store listing only`() {
        val own = "https://play.google.com/store/apps/details?id=io.github.josepacelli.opendisplay"
        assertEquals(own, PhoneReceiver.sanitizedStoreUrl(own))
        assertNull(
            PhoneReceiver.sanitizedStoreUrl(
                "https://play.google.com/store/apps/details?id=io.github.josepacelli.opendisplay.fake",
            ),
        )
        assertNull(PhoneReceiver.sanitizedStoreUrl("https://play.google.com/store/apps/details?id=com.other"))
    }

    @Test
    fun `rejects null and blank input`() {
        assertNull(PhoneReceiver.sanitizedStoreUrl(null))
        assertNull(PhoneReceiver.sanitizedStoreUrl(""))
        assertNull(PhoneReceiver.sanitizedStoreUrl("   "))
    }

    @Test
    fun `rejects malformed URIs`() {
        assertNull(PhoneReceiver.sanitizedStoreUrl("not a url"))
    }
}
