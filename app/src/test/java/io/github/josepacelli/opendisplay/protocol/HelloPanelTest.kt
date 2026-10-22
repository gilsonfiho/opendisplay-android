package io.github.josepacelli.opendisplay.protocol

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/** #166: `hello.panel` (PROTOCOL.md §6.7) — the Mac ignores the whole object unless pixels are
 * integers of at least 2 and scale is finite and positive, so anything else is left out. */
class HelloPanelTest {

    @Test
    fun `carries pixels and scale and nothing else`() {
        assertEquals(
            mapOf("pixelsWide" to 2880, "pixelsHigh" to 1800, "scale" to 2.25),
            HelloPanel.fields(2880, 1800, 2.25),
        )
    }

    @Test
    fun `keeps the current orientation as given`() {
        assertEquals(mapOf("pixelsWide" to 1800, "pixelsHigh" to 2880, "scale" to 2.25), HelloPanel.fields(1800, 2880, 2.25))
    }

    @Test
    fun `is left out before the panel size is known`() {
        assertNull(HelloPanel.fields(0, 0, 1.0))
    }

    @Test
    fun `is left out when a side is below 2 pixels`() {
        assertNull(HelloPanel.fields(1, 1800, 2.0))
        assertNull(HelloPanel.fields(2880, 1, 2.0))
    }

    @Test
    fun `is left out for a scale the Mac would reject`() {
        assertNull(HelloPanel.fields(2880, 1800, 0.0))
        assertNull(HelloPanel.fields(2880, 1800, -1.0))
        assertNull(HelloPanel.fields(2880, 1800, Double.NaN))
        assertNull(HelloPanel.fields(2880, 1800, Double.POSITIVE_INFINITY))
    }
}
