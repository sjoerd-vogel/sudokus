package com.vogel.sjoerd.sudokus.classic

import com.vogel.sjoerd.sudokus.board.Board
import com.vogel.sjoerd.sudokus.board.Cell
import com.vogel.sjoerd.sudokus.board.Coord
import com.vogel.sjoerd.sudokus.board.Sector
import com.vogel.sjoerd.sudokus.Extensions.selfTensor

//create a classic sudoku board
internal object CreateClassicBoard {
    internal operator fun <T> invoke(): Board<T> = Board(defaultSectors(), defaultCells())

    private fun <T> defaultCells(): Iterable<Cell<T>> = (1..9).selfTensor()
        .map { (column, row) -> Cell(Coord(column, row)) }

    private fun defaultSectors(): Iterable<Sector> = (1..3).selfTensor()
        .map { (column, row) -> Coord(column, row) }
        .map { sc ->
            Sector(
                (1..3).selfTensor()
                    .map { (column, row) -> Coord(column, row) }
                    .map { bc -> bc + (sc - Coord(1, 1)) * Coord(3, 3) }
            )
        }
}