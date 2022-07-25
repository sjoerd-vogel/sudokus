package sudokus

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private class IntRangeExtensionsKtTest {
    @Test
    @Suppress("EmptyRange")
    fun itShouldGetTheRightCountForVariousExamples() {
        assertEquals(10, (9 downTo 0).fastCount())
        assertEquals(5, IntProgression.fromClosedRange(8, 0, -2).fastCount())
        assertEquals(0, (9..0).fastCount())
        assertEquals(10, (0..9).fastCount())
        assertEquals(10, (-2..7).fastCount())
        assertEquals(10, (-9..0).fastCount())
        assertEquals(10, (-10..-1).fastCount())
    }
}