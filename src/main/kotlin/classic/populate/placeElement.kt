package classic.populate

import board.Board
import board.Coord
import board.Sector
import classic.isAllowedByGameRules

internal fun  placeElement(board: Board, sector: Sector, coord: Coord): Board {
    return board.set(
        coord,
        board.values.filter { isAllowedByGameRules(board, coord, it) }
//            .filter { sectorCompatible(board, coord, sector, it) }
            .randomOrNull() ?: throw RetryException(board)
    )
}