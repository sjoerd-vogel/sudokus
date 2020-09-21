package com.vogel.sjoerd.sudokus.classic

import com.vogel.sjoerd.sudokus.board.Board
import com.vogel.sjoerd.sudokus.board.Cell
import com.vogel.sjoerd.sudokus.board.Coord
import com.vogel.sjoerd.sudokus.board.State

internal fun <T> isAllowedByGameRules(board: Board<T>, coord: Coord, element: T): Boolean =
    notInSameColumn(board, coord, element) &&
            notInSameRow(board, coord, element) &&
            notInSameSector(board, coord, element)

private fun <T> notInSameRow(board: Board<T>, coord: Coord, element: T): Boolean =
    board.getRow(coord.row)
        .values()
        .none { it == element }

private fun <T> notInSameColumn(board: Board<T>, coord: Coord, element: T): Boolean =
    board.getColumn(coord.column).values().none { it == element }

private fun <T> notInSameSector(board: Board<T>, coord: Coord, element: T): Boolean =
    board.sectors.first { coord in it.coords }
        .coords.map { board.getCell(it) }
        .values()
        .none { it == element }

private fun <T> Iterable<Cell<T>>.values(): Iterable<T> = this.mapNotNull {
    if (it.state is State.Valued) it.state.value else null
}

private fun <T> Board<T>.getCell(coord: Coord): Cell<T> = cells
    .filter { it.coord == coord }.first()

private fun <T> Board<T>.getRow(row: Int): Iterable<Cell<T>> = cells
    .filter { it.coord.row == row }

private fun <T> Board<T>.getColumn(column: Int): Iterable<Cell<T>> = cells
    .filter { it.coord.column == column }