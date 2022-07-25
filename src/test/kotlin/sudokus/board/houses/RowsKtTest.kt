package sudokus.board.houses

import org.junit.jupiter.api.Test
import sudokus.TestBoard
import sudokus.board.Coord
import sudokus.parse.parse
import kotlin.test.assertEquals

internal class RowsKtTest{
    @Test
    fun itShouldSeparateOutRows() {
        val rows = parse(TestBoard.testBoard).rows()
        assertEquals(9, rows.size)
        assertEquals(
            setOf(
                Coord(column = 1, row = 1),
                Coord(column = 2, row = 1),
                Coord(column = 3, row = 1),
                Coord(column = 4, row = 1),
                Coord(column = 5, row = 1),
                Coord(column = 6, row = 1),
                Coord(column = 7, row = 1),
                Coord(column = 8, row = 1),
                Coord(column = 9, row = 1),
            ),
            rows[0].keys
        )
    }
}
