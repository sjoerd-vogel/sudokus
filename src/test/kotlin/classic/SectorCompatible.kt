package classic

import board.Board
import board.Coord
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SectorCompatible {

    private val verGood = listOf(
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
    )

    private val verBad = verGood.parse().set(Coord(2, 6), "1")

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
    }(verGood.parse()))

    @Test
    fun badHorizontal() = assertHorizontalIncompatible(
        1, 4, 1,"",
        "-------------------------------------",
        "| 1 | 2 | 3 | 4 | 5 | 6 |   |   |   |",
        "-------------------------------------",
        "| 4 | 5 | 6 | 8 | 7 | 1 |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------",
        "|   |   |   |   |   |   |   |   |   |",
        "-------------------------------------"
    )

    @Test
    fun goodHorizontal() = assertHorizontalCompatible(
        1, 4, 1, "",
        "-------------------------------------\n",
        "| 1 | 2 | 3 | 4 | 5 | 6 |   |   |   |\n",
        "-------------------------------------\n",
        "| 4 | 5 | 6 | 8 | 7 |   |   |   |   |\n",
        "-------------------------------------\n",
        "|   |   |   |   |   |   |   |   |   |\n",
        "-------------------------------------\n",
        "|   |   |   |   |   |   |   |   |   |\n",
        "-------------------------------------\n",
        "|   |   |   |   |   |   |   |   |   |\n",
        "-------------------------------------\n",
        "|   |   |   |   |   |   |   |   |   |\n",
        "-------------------------------------\n",
        "|   |   |   |   |   |   |   |   |   |\n",
        "-------------------------------------\n",
        "|   |   |   |   |   |   |   |   |   |\n",
        "-------------------------------------\n",
        "|   |   |   |   |   |   |   |   |   |\n",
        "-------------------------------------"
    )

    private fun assertHorizontalIncompatible(
        firstCellColumn: Int,
        secondCellColumn: Int,
        cellRow: Int,
        vararg grid: String
    ): Unit = assertFalse(isHorizontalCompatible(firstCellColumn, secondCellColumn, cellRow))

    private fun assertHorizontalCompatible(
        firstCellColumn: Int,
        secondCellColumn: Int,
        cellRow: Int,
        vararg grid: String
    ): Unit = assertTrue(isHorizontalCompatible(firstCellColumn, secondCellColumn, cellRow))

    private fun isHorizontalCompatible(
        firstCellColumn: Int,
        secondCellColumn: Int,
        cellRow: Int,
        vararg grid: String
    ): Boolean =
        { board: Board<String> ->
            areSectorsHorizontalCompatible(board,
                board.sectors.first { Coord(firstCellColumn, cellRow) in it.coords },
                board.sectors.first { Coord(secondCellColumn, cellRow) in it.coords })
        }(grid.parse())


}