package sudokus.classic.populate

import sudokus.board.Board
import sudokus.board.Coord
import sudokus.board.Sector
import sudokus.classic.isAllowedByGameRules

internal fun  placeElement(board: Board, sector: Sector, coord: Coord): Board {
    return board.set(
        coord,
        board.values.filter { isAllowedByGameRules(board, coord, it) }
            .randomOrNull() ?: throw RetryException(board)
    )
}