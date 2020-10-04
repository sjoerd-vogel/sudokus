package classic.populate

import board.Board
import board.Coord
import board.Sector
import classic.isAllowedByGameRules

internal fun <T> placeElement(board: Board<T>, sector: Sector, coord: Coord, elements: Iterable<T>): Board<T> {
    return board.set(
        coord,
        elements.filter { isAllowedByGameRules(board, coord, it) }
//            .filter { sectorCompatible(board, coord, sector, it) }
            .randomOrNull() ?: throw RetryException(board)
    )
}