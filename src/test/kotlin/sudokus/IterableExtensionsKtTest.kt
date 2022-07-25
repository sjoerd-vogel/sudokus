package sudokus

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class IterableExtensionsKtTest {
    @Test
    fun itShouldReturnCombinations(){
        assertEquals(
            "[[1, 3], [1, 4], [2, 3], [2, 4]]", listOf((1..2), (3..4))
                .combinations().toList().toString()
        )
        val combs2 = listOf((1..3), (2..4), (2..3))
            .combinations().toList()
        //no duplicates
        assertTrue(combs2.groupBy { it }.values.map { it.count() }.fold(true) { acc, i -> acc && i < 2 })
        assertEquals(
            "[[1, 2, 2], [1, 2, 3], [1, 3, 2], [1, 3, 3], [1, 4, 2], [1, 4, 3], [2, 2, 2], [2, 2, 3], [2, 3, 2], [2, 3, 3], [2, 4, 2], [2, 4, 3], [3, 2, 2], [3, 2, 3], [3, 3, 2], [3, 3, 3], [3, 4, 2], [3, 4, 3]]",
            combs2.toString()
        )
    }

    @Test
    fun itShouldProperlyTensorTogetherBothLists() {
        assertEquals(
            listOf(
                Pair(1, 4), Pair(1, 5), Pair(1, 6),
                Pair(2, 4), Pair(2, 5), Pair(2, 6),
                Pair(3, 4), Pair(3, 5), Pair(3, 6),
            ),

            (1..3).tensor(4..6)
        )
    }

    @Test
    fun itShouldProperlyTensorTheListWithItself() {
        assertEquals(
            listOf(
                Pair(1, 1), Pair(1, 2), Pair(1, 3),
                Pair(2, 1), Pair(2, 2), Pair(2, 3),
                Pair(3, 1), Pair(3, 2), Pair(3, 3),
            ),

            (1..3).selfTensor()
        )
    }

    @Test
    fun itShouldConcludeAllValuesAreTrue() {
        assertTrue(listOf(true, true, true).allTrue())
    }

    @Test
    fun itShouldConcludeNotAllValuesAreTrue() {
        assertFalse(listOf(true, false, true).allTrue())
    }

    @Test
    fun itShouldConcludeAllValuesAreFalse() {
        assertTrue(listOf(false, false, false).allFalse())
    }

    @Test
    fun itShouldConcludeNotAllValuesAreFalse() {
        assertFalse(listOf(true, false, true).allFalse())
    }
}
