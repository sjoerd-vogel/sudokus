package classic

import board.Board
import board.Cell
import board.Coord
import board.State
import classic.populate.areSectorsHorizontalCompatible
import classic.populate.areSectorsVerticalCompatible
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SectorCompatible {
    private val verGood = createClassicBoard<String>()
        .set(Coord(1, 1), "1").set(Coord(2, 1), "4")
        .set(Coord(1, 2), "2").set(Coord(2, 2), "5")
        .set(Coord(1, 3), "3").set(Coord(2, 3), "6")

        .set(Coord(1, 4), "4").set(Coord(2, 4), "8")
        .set(Coord(1, 5), "5").set(Coord(2, 5), "7")
        .set(Coord(1, 6), "6")


    private val verBad = verGood.set(Coord(2, 6), "1")

    @Test
    fun badVertical() = assertFalse({ board: Board<String> ->
        areSectorsVerticalCompatible(board,
            board.sectors.first { Coord(1, 1) in it.coords },
            board.sectors.first { Coord(1, 4) in it.coords })
    }(verBad))

    @Test
    fun goodVertical() = assertTrue({ board: Board<String> ->
        areSectorsVerticalCompatible(board,
            board.sectors.first { Coord(1, 1) in it.coords },
            board.sectors.first { Coord(1, 4) in it.coords })
    }(verGood))

    @Test
    fun badHorizontal() = assertFalse({ board: Board<String> ->
        areSectorsHorizontalCompatible(board,
            board.sectors.first { Coord(1, 1) in it.coords },
            board.sectors.first { Coord(4, 1) in it.coords })
    }(verBad.transpose()))

    @Test
    fun goodHorizontal() = assertTrue({ board: Board<String> ->
        areSectorsHorizontalCompatible(board,
            board.sectors.first { Coord(1, 1) in it.coords },
            board.sectors.first { Coord(1, 4) in it.coords })
    }(verGood.transpose()))

    private fun <T> Board<T>.transpose(): Board<T> {
        val empty = Board<T>(sectors, cells.map { Cell(it.coord, State.Empty) })
        tailrec fun work(cells: Iterable<Cell<T>>, board: Board<T>): Board<T> {
            if (cells.none()) return board
            val first = cells.first()
            val tail = cells.tail()
            return if (first.state !is State.Valued) work(tail, board)
            else work(tail, board.set(first.coord.transpose(), (first.state as State.Valued).value))
        }
        return work(cells, empty)
    }

    private fun Coord.transpose(): Coord = Coord(row = column, column = row)
    private fun <T> Iterable<T>.tail() = drop(1)
}