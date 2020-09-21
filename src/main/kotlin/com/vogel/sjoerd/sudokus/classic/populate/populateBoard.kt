package com.vogel.sjoerd.sudokus.classic.populate

import com.vogel.sjoerd.sudokus.board.Board
import com.vogel.sjoerd.sudokus.board.State
import com.vogel.sjoerd.sudokus.classic.createClassicBoard

//add elements to cells of classic board
internal tailrec fun <T> populateBoard(elements: Iterable<T>): Board<T> {
    try {
        return fillSectors(createClassicBoard(), elements)
    } catch (re: RetryException) {
    }
    return populateBoard(elements)
}

private fun <T> fillSectors(board: Board<T>, elements: Iterable<T>): Board<T> {

    tailrec fun worker(board: Board<T>): Board<T> {
        val empties = board.getUnfilledSectors()

        return if (empties.none()) board
        else worker(populateSector(board, empties.first(), elements))
    }
    return worker(board)
}

private fun <T> Board<T>.getUnfilledSectors() = sectors.filter { sec ->
    sec.coords.any { secc ->
        cells.any { cel -> cel.state == State.Empty && cel.coord == secc }
    }
}