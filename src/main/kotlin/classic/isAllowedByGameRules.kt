package classic

import board.Board
import board.Cell
import board.Coord
import board.State

internal fun  isAllowedByGameRules(board: Board, coord: Coord, element: Int): Boolean =
    notInSameColumn(board, coord, element) &&
            notInSameRow(board, coord, element) &&
            notInSameSector(board, coord, element)

private fun  notInSameRow(board: Board, coord: Coord, element: Int): Boolean =
    board.getRow(coord.row)
        .values()
        .none { it == element }

private fun  notInSameColumn(board: Board, coord: Coord, element: Int): Boolean =
    board.getColumn(coord.column).values().none { it == element }

private fun  notInSameSector(board: Board, coord: Coord, element: Int): Boolean =
    board.sectors.first { coord in it.coords }
        .coords.map { board.getCell(it) }
        .values()
        .none { it == element }

private fun  Iterable<Cell>.values(): Iterable<Int> = this.mapNotNull {
    if (it.state is State.Valued) it.state.value else null
}

private fun  Board.getCell(coord: Coord): Cell = cells.first { it.coord == coord }

private fun  Board.getRow(row: Int): Iterable<Cell> = cells
    .filter { it.coord.row == row }

private fun  Board.getColumn(column: Int): Iterable<Cell> = cells
    .filter { it.coord.column == column }