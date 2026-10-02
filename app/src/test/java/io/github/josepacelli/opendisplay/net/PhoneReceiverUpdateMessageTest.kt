package io.github.josepacelli.opendisplay.net

import org.junit.Assert.assertEquals
import org.junit.Test

/** #157: `message` on `updateRequired` comes from an unauthenticated peer — must be
 * bounded in length before it reaches the UI. */
class PhoneReceiverUpdateMessageTest {

    @Test
    fun `leaves a short message unchanged`() {
        val message = "Atualize o OpenDisplay para continuar usando este segundo display."
        assertEquals(message, PhoneReceiver.sanitizedUpdateMessage(message))
    }

    @Test
    fun `leaves a message at exactly the limit unchanged`() {
        val message = "a".repeat(300)
        assertEquals(message, PhoneReceiver.sanitizedUpdateMessage(message))
    }

    @Test
    fun `truncates a message over the limit`() {
        val message = "a".repeat(5_000)
        val result = PhoneReceiver.sanitizedUpdateMessage(message)
        assertEquals(300, result.length)
        assertEquals("a".repeat(300), result)
    }

    @Test
    fun `leaves an empty message unchanged`() {
        assertEquals("", PhoneReceiver.sanitizedUpdateMessage(""))
    }
}
