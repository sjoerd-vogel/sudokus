package sudokus.board.houses

import sudokus.board.Board
import sudokus.board.Cell
import sudokus.board.Coord

private val rowIndices = (1..9)

fun Board.rows(): List<Map<Coord, Cell>> =
    rowIndices.map { row -> this.filterKeys { k -> k.row == row } }
