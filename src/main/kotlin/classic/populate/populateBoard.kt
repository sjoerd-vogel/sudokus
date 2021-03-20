package classic.populate

import board.Board
import board.State
import classic.createClassicBoard

//add elements to cells of classic board
internal tailrec fun populateBoard(elements: Iterable<Int>): Board {
    try {
        return fillSectors(createClassicBoard(elements))
    } catch (re: RetryException) {
        re //breakpoint helper
    }
    return populateBoard(elements)
}

private fun fillSectors(board: Board): Board {

    tailrec fun worker(board: Board): Board {
        val empties = board.getUnfilledSectors()

        return if (empties.none()) board
        else worker(populateSector(board, empties.first()))
    }
    return worker(board)
}

private fun Board.getUnfilledSectors() = sectors.filter { sec ->
    sec.coords.any { secc ->
        cells.any { cel -> cel.state == State.Empty && cel.coord == secc }
    }
}