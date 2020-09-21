package classic.populate

import board.Board
import board.Coord
import classic.isAllowedByGameRules

internal fun <T> placeElement(board: Board<T>, coord: Coord, elements: Iterable<T>): Board<T> {
    return board.set(
        coord,
        elements.filter { isAllowedByGameRules(board, coord, it) }
            .randomOrNull() ?: throw RetryException(board)
    )
}