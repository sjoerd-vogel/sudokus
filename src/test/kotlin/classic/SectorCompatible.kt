package classic

import board.Board
import board.Cell
import board.Coord
import board.State
import classic.populate.areSectorsHorizontalCompatible
import classic.populate.areSectorsVerticalCompatible
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SectorCompatible {

    private val verGoodStr = listOf(
        "-------------------------------------",
        "| 1 | 4 |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "| 2 | 5 |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "| 3 | 6 |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "| 4 | 8 |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "| 5 | 7 |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "| 6 |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------"
    ).joinToString("", "", "")

    private val verGood = createClassicBoard((1..9).map { i -> i.toString() })
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

    @Test
    fun parse() {
        val str = listOf(
            "-------------------------------------\n",
            "| 5 | 1 | 7 | 3 | 2 | 6 | 9 | 4 | 8 |\n",
            "-------------------------------------\n",
            "| 4 | 2 | 9 | 5 | 8 | 7 | 3 | 1 | 6 |\n",
            "-------------------------------------\n",
            "| 8 | 6 | 3 | 1 | 9 | 4 | 2 | 7 | 5 |\n",
            "-------------------------------------\n",
            "| 7 | 4 | 5 | 8 | 3 | 2 | 1 | 6 | 9 |\n",
            "-------------------------------------\n",
            "| 1 | 8 | 6 | 4 | 5 | 9 | 7 | 3 | 2 |\n",
            "-------------------------------------\n",
            "| 9 | 3 | 2 | 6 | 7 | 1 | 8 | 5 | 4 |\n",
            "-------------------------------------\n",
            "| 2 | 9 | 4 | 7 | 1 | 5 | 6 | 8 | 3 |\n",
            "-------------------------------------\n",
            "| 6 | 7 | 8 | 9 | 4 | 3 | 5 | 2 | 1 |\n",
            "-------------------------------------\n",
            "| 3 | 5 | 1 | 2 | 6 | 8 | 4 | 9 | 7 |\n",
            "-------------------------------------"
        ).joinToString("", "", "")

        assertEquals(str.parse().toString(), str)
        assertEquals(
            str
                .replace("\n", "")
                .parse().toString(),
            str
        )
        assertEquals(verGoodStr.parse().toString().replace("\n", ""), verGoodStr)

    }

    private fun <T> Board<T>.transpose(): Board<T> {
        val empty = Board<T>(values, sectors, cells.map { Cell(it.coord, State.Empty) })
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

    private fun String.parse(): Board<String> =
        replace("\n", "")
            .replace("-", "")
            .replace("||", "|")
            .removeSuffix("|")
            .chunked(4)
            .map { it.get(2) }
            .map(Any::toString)
            .chunked(9)
            .flatMapIndexed { row, list ->
                list.mapIndexed { column, string ->
                    if (string == " ") Cell<String>(Coord(row = row + 1, column = column + 1), State.Empty)
                    else Cell(Coord(row = row + 1, column = column + 1), State.Valued(string))
                }
            }.fold(
                createClassicBoard((1..9).map { i -> i.toString() }),
                { board, cell -> board.set(cell) }
            )


}