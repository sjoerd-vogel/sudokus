package classic.populate

import board.Board
import board.Board.Companion.getCellsBySector
import board.Sector
import board.State

internal fun populateSector(board: Board, sector: Sector): Board {
    tailrec fun worker(board: Board): Board {
        val empties = board.getCellsBySector(sector)
            .filter { it.state == State.Empty }

        return if (empties.none()) board
        else worker(placeElement(board, sector, empties.first().coord))
    }
    return worker(board)
}

