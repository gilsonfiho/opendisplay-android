package io.github.josepacelli.opendisplay.net

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** #186: an unknown control type is logged once, not per message (PROTOCOL.md §6), and a peer
 * inventing types can't grow the set forever. */
class FirstSeenTest {

    @Test
    fun `reports a key only the first time`() {
        val firstSeen = FirstSeen(limit = 4)
        assertTrue(firstSeen.isFirst("streamConfig"))
        assertFalse(firstSeen.isFirst("streamConfig"))
        assertTrue(firstSeen.isFirst("power"))
    }

    @Test
    fun `goes quiet once the limit is reached`() {
        val firstSeen = FirstSeen(limit = 2)
        assertTrue(firstSeen.isFirst("a"))
        assertTrue(firstSeen.isFirst("b"))
        assertFalse(firstSeen.isFirst("c"))
        assertFalse(firstSeen.isFirst("a"))
    }
}
