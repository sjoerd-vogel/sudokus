package sudokus.generate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import sudokus.board.Coord
import sudokus.selfTensor

private class EmptyBoardKtTest {

    @Test
    fun itShouldCreateAnEmptyBoard() {
        assertEquals(81, emptyBoard.values.size)
        assertTrue(emptyBoard.values.all { it.value == null })
        assertTrue(emptyBoard.values.none { it.fixed })
        assertTrue((1..9).selfTensor().all { (row, column) ->
            emptyBoard.keys.contains(Coord(row = row, column = column))
        })
    }
}
