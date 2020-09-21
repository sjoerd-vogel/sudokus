package com.vogel.sjoerd.sudokus.classic.populate

import com.vogel.sjoerd.sudokus.board.Board
import com.vogel.sjoerd.sudokus.board.Extensions.getUnfilledSectors
import com.vogel.sjoerd.sudokus.classic.CreateClassicBoard

internal object PopulateBoard {
    //add elements to cells of classic board
    tailrec operator fun <T> invoke(elements: Iterable<T>): Board<T> {
        try {
            return fillSectors(elements)
        } catch (re: RetryException) {
        }
        return invoke(elements)
    }

    private fun <T> fillSectors(elements: Iterable<T>): Board<T> {

        tailrec fun worker(board: Board<T> = CreateClassicBoard()): Board<T> {
            val empties = board.getUnfilledSectors()

            return if (empties.none()) board
            else worker(PopulateSector(board, empties.first(), elements))
        }
        return worker()
    }
}