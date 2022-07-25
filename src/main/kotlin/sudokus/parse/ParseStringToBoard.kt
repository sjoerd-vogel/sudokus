package sudokus.parse

import sudokus.board.Board
import sudokus.board.Cell
import sudokus.board.Coord

private val IS_INT_PATTERN = "\\d+".toRegex()
private fun String.isInt() = this.matches(IS_INT_PATTERN)

fun parse(str: String): Board = str.replace("||", "|")
    .split("|")
    .filter { it == "   " || it.trim().isInt() }
    .map { if (it == "   ") null else it.trim().toInt() }
    .mapIndexed { i, v -> Pair(Coord(i % 9 + 1, i / 9 + 1), Cell(v)) }
    .toMap()
