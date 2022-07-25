package sudokus.parse

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import sudokus.TestBoard.testBoard
import sudokus.board.print

private class ParseStringToBoardKtTest {

    @Test
    fun itShouldParseToTheExpectedBoard() {
        val board = parse(testBoard)
        assertEquals(81, board.keys.size)
        assertEquals(2, board.values.count { it.value != null })
        assertTrue(board.values.none { it.fixed })
        assertEquals(testBoard, board.print())
    }

}
