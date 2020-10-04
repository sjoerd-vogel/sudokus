package external.iterable

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TensorTests {
    @Test
    fun correctlyTensorShortLists() =
        assertEquals(
            listOf(
                Pair("a", 1),
                Pair("a", 2),
                Pair("b", 1),
                Pair("b", 2),
            ),

            listOf("a", "b").tensor(listOf(1, 2))
        )

    @Test
    fun correctlySelfTensorShortList() =
        assertEquals(
            listOf(
                Pair("a", "a"),
                Pair("a", "b"),
                Pair("b", "a"),
                Pair("b", "b"),
            ),

            listOf("a", "b").selfTensor()
        )
}