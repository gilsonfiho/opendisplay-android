package io.github.josepacelli.opendisplay.net

import java.net.InetAddress
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** #168: PROTOCOL.md §1 has the receiver adopt a new connection. Like the official receiver,
 * one from another host during a live session must first prove itself by sending bytes; the
 * rest is adopted at once. */
class PhoneReceiverNeedsProofTest {

    private val mac = InetAddress.getByName("192.168.1.5")
    private val other = InetAddress.getByName("192.168.1.99")

    @Test
    fun `adopts any address at once when idle`() {
        assertFalse(PhoneReceiver.needsProof(currentAddress = null, hasActiveSession = false, newAddress = other))
    }

    @Test
    fun `adopts a reconnect from the same address at once`() {
        assertFalse(PhoneReceiver.needsProof(currentAddress = mac, hasActiveSession = true, newAddress = mac))
    }

    @Test
    fun `makes a different address prove itself while a session is live`() {
        assertTrue(PhoneReceiver.needsProof(currentAddress = mac, hasActiveSession = true, newAddress = other))
    }

    @Test
    fun `makes a TCP connection prove itself while a USB session is live`() {
        assertTrue(PhoneReceiver.needsProof(currentAddress = null, hasActiveSession = true, newAddress = mac))
    }
}
