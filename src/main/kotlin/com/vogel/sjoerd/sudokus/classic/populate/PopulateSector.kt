package com.vogel.sjoerd.sudokus.classic.populate

import com.vogel.sjoerd.sudokus.board.Board
import com.vogel.sjoerd.sudokus.board.Sector
import com.vogel.sjoerd.sudokus.board.State
import com.vogel.sjoerd.sudokus.board.Extensions.getCellsBySector

internal object PopulateSector {
    internal operator fun <T> invoke(board: Board<T>, sector: Sector, elements: Iterable<T>): Board<T> {
        tailrec fun worker(board: Board<T>): Board<T> {
            val empties = board.getCellsBySector(sector)
                .filter { it.state == State.Empty }

            return if (empties.none()) board
            else worker(PlaceElement(board, empties.first().coord, elements))
        }
        return worker(board)
    }
}