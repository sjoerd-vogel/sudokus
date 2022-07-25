package sudokus.board.houses

import sudokus.board.Board
import sudokus.board.Cell
import sudokus.board.Coord

private val columnIndices = (1..9)
fun Board.columns(): List<Map<Coord, Cell>> =
    columnIndices.map { column -> this.filterKeys { k -> k.column == column } }
