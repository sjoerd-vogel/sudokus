package sudokus.math

import org.junit.jupiter.api.Test
import sudokus.pow
import kotlin.test.assertEquals

private class PowKtTest {
    @Test
    fun itShouldGetTheseExamplesRight() {
        assertEquals(8, 2.pow(3))
        assertEquals(9, 3.pow(2))
    }
}