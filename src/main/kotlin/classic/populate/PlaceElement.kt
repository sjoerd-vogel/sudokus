package classic.populate

import board.Board
import board.Coord
import board.Sector
import classic.isAllowedByGameRules

internal fun <T> placeElement(board: Board<T>, sector: Sector, coord: Coord, elements: Iterable<T>): Board<T> =
    elements.filter { isAllowedByGameRules(board, coord, it) }
        .map { board.set(coord, it) }
        .filter { sectorCompatible(it) }
        .randomOrNull() ?: throw RetryException(board)