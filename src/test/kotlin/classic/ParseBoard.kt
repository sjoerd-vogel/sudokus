package classic

import board.Board
import board.Cell
import board.Coord
import board.State
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private fun Iterable<String>.parse() = joinToString("", "", "")
    .parse()

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
        }.fold(createClassicBoard(), { board, cell -> board.set(cell) })

private val filled = listOf(
    "-------------------------------------",
    "| 5 | 1 | 7 | 3 | 2 | 6 | 9 | 4 | 8 |",
    "-------------------------------------",
    "| 4 | 2 | 9 | 5 | 8 | 7 | 3 | 1 | 6 |",
    "-------------------------------------",
    "| 8 | 6 | 3 | 1 | 9 | 4 | 2 | 7 | 5 |",
    "-------------------------------------",
    "| 7 | 4 | 5 | 8 | 3 | 2 | 1 | 6 | 9 |",
    "-------------------------------------",
    "| 1 | 8 | 6 | 4 | 5 | 9 | 7 | 3 | 2 |",
    "-------------------------------------",
    "| 9 | 3 | 2 | 6 | 7 | 1 | 8 | 5 | 4 |",
    "-------------------------------------",
    "| 2 | 9 | 4 | 7 | 1 | 5 | 6 | 8 | 3 |",
    "-------------------------------------",
    "| 6 | 7 | 8 | 9 | 4 | 3 | 5 | 2 | 1 |",
    "-------------------------------------",
    "| 3 | 5 | 1 | 2 | 6 | 8 | 4 | 9 | 7 |",
    "-------------------------------------"
)
private val empty = listOf(
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
    "-------------------------------------",
    "|   |   |   |   |   |   |   |   |   |",
    "-------------------------------------",
    "|   |   |   |   |   |   |   |   |   |",
    "-------------------------------------"
)
private val partial = listOf(
    "-------------------------------------",
    "|   |   |   |   |   |   |   |   |   |",
    "-------------------------------------",
    "|   |   |   |   |   |   |   |   |   |",
    "-------------------------------------",
    "|   |   |   | 1 |   |   |   |   |   |",
    "-------------------------------------",
    "|   |   |   |   |   |   |   |   |   |",
    "-------------------------------------",
    "|   |   | 2 |   |   |   |   |   |   |",
    "-------------------------------------",
    "|   |   |   |   |   |   |   |   |   |",
    "-------------------------------------",
    "|   | 3 |   |   |   |   |   |   |   |",
    "-------------------------------------",
    "|   |   |   |   |   |   |   |   |   |",
    "-------------------------------------",
    "|   |   |   |   |   |   |   |   |   |",
    "-------------------------------------"
)

class ParseTests {
    @Test
    fun itShouldRecoverTheInputUponParseAndToStringForAFilledBoard() =
        assertEquals(
            filled.parse().toString(),
            filled.joinToString("\n", "", "")
        )

    @Test
    fun itShouldRecoverTheInputUponParseAndToStringForAnEmptyBoard() =
        assertEquals(
            empty.parse().toString(),
            empty.joinToString("\n", "", "")
        )

    @Test
    fun itShouldRecoverTheInputUponParseAndToStringForAPartiallyFilledBoard() =
        assertEquals(
            partial.parse().toString(),
            partial.joinToString("\n", "", "")
        )

}