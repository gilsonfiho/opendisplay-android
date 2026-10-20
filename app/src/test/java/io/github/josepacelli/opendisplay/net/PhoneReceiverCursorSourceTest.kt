package io.github.josepacelli.opendisplay.net

import java.net.InetAddress
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** #155: the UDP cursor channel has no auth either (SECURITY.md/SCR-001) — a datagram must
 * come from the same host as the active TCP peer to be trusted. */
class PhoneReceiverCursorSourceTest {

    private val mac = InetAddress.getByName("192.168.1.5")
    private val attacker = InetAddress.getByName("192.168.1.99")

    @Test
    fun `trusts a datagram from the active TCP peer`() {
        assertTrue(PhoneReceiver.isTrustedCursorSource(tcpPeerAddress = mac, source = mac))
    }

    @Test
    fun `rejects a datagram from a different address`() {
        assertFalse(PhoneReceiver.isTrustedCursorSource(tcpPeerAddress = mac, source = attacker))
    }

    @Test
    fun `rejects any datagram when there is no active TCP peer`() {
        assertFalse(PhoneReceiver.isTrustedCursorSource(tcpPeerAddress = null, source = mac))
    }
}
