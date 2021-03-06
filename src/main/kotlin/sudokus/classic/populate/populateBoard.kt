package sudokus.classic.populate

import sudokus.board.Board
import sudokus.board.State
import sudokus.classic.createClassicBoard

//add elements to cells of sudokus.classic sudokus.board
internal tailrec fun populateBoard(): Board {
    try {
        return fillSectors(createClassicBoard())
    } catch (re: RetryException) {
        re //breakpoint helper
    }
    return populateBoard()
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