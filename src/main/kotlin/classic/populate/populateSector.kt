package classic.populate

import board.Board
import board.Board.Companion.getCellsBySector
import board.Sector
import board.State

internal fun <T> populateSector(board: Board<T>, sector: Sector, elements: Iterable<T>): Board<T> {
    tailrec fun worker(board: Board<T>): Board<T> {
        val empties = board.getCellsBySector(sector)
            .filter { it.state == State.Empty }

        return if (empties.none()) board
        else worker(placeElement(board, empties.first().coord, elements))
    }
    return worker(board)
}
