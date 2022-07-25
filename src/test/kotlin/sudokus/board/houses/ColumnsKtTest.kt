package sudokus.board.houses

import org.junit.jupiter.api.Test
import sudokus.TestBoard.testBoard
import sudokus.board.Coord
import sudokus.parse.parse
import kotlin.test.assertEquals

internal class ColumnsKtTest {
    @Test
    fun itShouldSeparateOutColumns() {
        val columns = parse(testBoard).columns()
        assertEquals(9, columns.size)
        assertEquals(
            setOf(
                Coord(column = 1, row = 1),
                Coord(column = 1, row = 2),
                Coord(column = 1, row = 3),
                Coord(column = 1, row = 4),
                Coord(column = 1, row = 5),
                Coord(column = 1, row = 6),
                Coord(column = 1, row = 7),
                Coord(column = 1, row = 8),
                Coord(column = 1, row = 9),
            ),
            columns[0].keys
        )
    }
}
