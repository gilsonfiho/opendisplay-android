package io.github.josepacelli.opendisplay.net

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class PacketInputStreamTest {
    /** Feeds [packets] one per raw read; `null` entries are EOF (-1), empty ones are ZLPs. */
    private fun streamOf(vararg packets: ByteArray?, requestedSizes: MutableList<Int>? = null): PacketInputStream {
        val queue = ArrayDeque(packets.toList())
        return PacketInputStream { b, off, len ->
            requestedSizes?.add(len)
            val next = queue.removeFirstOrNull()
            when {
                next == null -> -1
                else -> {
                    next.copyInto(b, off)
                    next.size
                }
            }
        }
    }

    @Test
    fun alwaysAsksForWhole16KBPackets() {
        val sizes = mutableListOf<Int>()
        val stream = streamOf(ByteArray(10) { 1 }, requestedSizes = sizes)
        stream.read(ByteArray(4), 0, 4)
        assertEquals(listOf(PacketInputStream.PACKET_READ), sizes)
    }

    @Test
    fun zeroLengthPacketIsNotEndOfStream() {
        val stream = streamOf(byteArrayOf(1, 2), byteArrayOf(), byteArrayOf(3))
        val out = ByteArray(3)
        var got = 0
        while (got < 3) got += stream.read(out, got, 3 - got)
        assertArrayEquals(byteArrayOf(1, 2, 3), out)
    }

    @Test
    fun frameSpanningPacketsIsServedInOrder() {
        val first = ByteArray(PacketInputStream.PACKET_READ) { (it % 251).toByte() }
        val second = ByteArray(5) { 9 }
        val stream = streamOf(first, second)
        val out = ByteArray(first.size + second.size)
        var got = 0
        while (got < out.size) got += stream.read(out, got, out.size - got)
        assertArrayEquals(first + second, out)
    }

    @Test
    fun endlessZeroLengthPacketsEndTheStream() {
        val stream = PacketInputStream { _, _, _ -> 0 }
        assertEquals(-1, stream.read(ByteArray(4), 0, 4))
    }

    @Test
    fun negativeReadIsEndOfStream() {
        assertEquals(-1, streamOf(null).read())
    }

    @Test
    fun singleByteReadAndAvailable() {
        val stream = streamOf(byteArrayOf(0xFF.toByte(), 7))
        assertEquals(0, stream.available())
        assertEquals(255, stream.read())
        assertEquals(1, stream.available())
        assertEquals(7, stream.read())
    }

    @Test
    fun zeroLengthReadReturnsZeroWithoutTouchingTheWire() {
        assertEquals(0, PacketInputStream { _, _, _ -> error("no read expected") }.read(ByteArray(1), 0, 0))
    }
}
