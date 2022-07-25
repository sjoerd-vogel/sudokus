package sudokus.board.houses

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import sudokus.TestBoard.testBoard
import sudokus.board.Coord
import sudokus.parse.parse

internal class BoxesKtTest {
    @Test
    fun itShouldProduceBoxesAndTheFirstShouldMatchExpectedCoords() {
        val boxes = parse(testBoard).boxes()
        assertEquals(9, boxes.size)
        assertEquals(
            setOf(
                Coord(1, 1), Coord(2, 1), Coord(3, 1),
                Coord(1, 2), Coord(2, 2), Coord(3, 2),
                Coord(1, 3), Coord(2, 3), Coord(3, 3),
            ), boxes[0].keys
        )
    }
}
