// Derived from PacketInputStream in Android/app/src/main/java/app/opendisplay/android/net/AccessoryLink.kt, peetzweg/opendisplay.
// Copyright (c) 2026 Philip Poloczek. Licensed under GPL-3.0.

package io.github.josepacelli.opendisplay.net

import java.io.InputStream

/**
 * Serves arbitrary reads from whole 16 KB reads of the USB accessory (PROTOCOL.md §2.3: the
 * receiver MUST read with 16 KB buffers and MUST treat a zero-length read as a zero-length
 * packet, not as end of stream). `FileInputStream` would report that 0-byte read as EOF.
 *
 * Pure, no Android dependency: the raw read is injected, so it runs on the JVM.
 *
 * @param readPacket reads up to `len` bytes into `b` at `off`; returns the count, 0 for a
 * zero-length packet, or a negative value at end of stream.
 */
internal class PacketInputStream(
    private val readPacket: (ByteArray, Int, Int) -> Int,
) : InputStream() {
    private val buffer = ByteArray(PACKET_READ)
    private var position = 0
    private var limit = 0

    /** @return the next byte, or -1 at end of stream. */
    override fun read(): Int {
        if (!fill()) return -1
        return buffer[position++].toInt() and 0xff
    }

    /** @return bytes copied into [b], or -1 at end of stream. */
    override fun read(b: ByteArray, off: Int, len: Int): Int {
        if (len == 0) return 0
        if (!fill()) return -1
        val n = minOf(len, limit - position)
        System.arraycopy(buffer, position, b, off, n)
        position += n
        return n
    }

    /** @return bytes left in the packet already read. */
    override fun available(): Int = limit - position

    /** Reads the next non-empty packet if the current one is drained.
     * @return false at end of stream. */
    private fun fill(): Boolean {
        if (position < limit) return true
        var empty = 0
        while (true) {
            val n = readPacket(buffer, 0, PACKET_READ)
            if (n < 0) return false
            if (n > 0) {
                position = 0
                limit = n
                return true
            }
            if (++empty >= MAX_EMPTY_READS) return false
        }
    }

    companion object {
        const val PACKET_READ = 16384
        private const val MAX_EMPTY_READS = 64
    }
}
