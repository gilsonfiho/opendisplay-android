package io.github.josepacelli.opendisplay.ui

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.PI

/** `AXIS_TILT` (0 = pen straight up) -> wire protocol altitude (pi/2 = straight up), the one
 * piece of stylus math in [VideoSurface] that doesn't need a real `MotionEvent` to exercise —
 * see `altitudeFromAndroidTilt`'s doc for why this conversion exists. */
class VideoSurfaceTiltTest {

    @Test
    fun `pen straight up reports the perpendicular altitude`() {
        assertEquals(PI / 2, altitudeFromAndroidTilt(0f), 1e-9)
    }

    @Test
    fun `tilting away from perpendicular lowers the altitude by the same amount`() {
        assertEquals(PI / 2 - 0.5, altitudeFromAndroidTilt(0.5f), 1e-6)
    }

    @Test
    fun `pen fully flat (tilt of pi over 2) reports zero altitude`() {
        assertEquals(0.0, altitudeFromAndroidTilt((PI / 2).toFloat()), 1e-6)
    }
}
