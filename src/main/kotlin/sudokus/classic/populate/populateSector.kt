package sudokus.classic.populate

import sudokus.board.Board
import sudokus.board.Board.Companion.getCellsBySector
import sudokus.board.Sector
import sudokus.board.State

internal fun populateSector(board: Board, sector: Sector): Board {
    tailrec fun worker(board: Board): Board {
        val empties = board.getCellsBySector(sector)
            .filter { it.state == State.Empty }

        return if (empties.none()) board
        else worker(placeElement(board, sector, empties.first().coord))
    }
    return worker(board)
}

