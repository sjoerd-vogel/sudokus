package com.vogel.sjoerd.sudokus.classic.populate

import com.vogel.sjoerd.sudokus.board.Board
import com.vogel.sjoerd.sudokus.board.Coord
import com.vogel.sjoerd.sudokus.classic.isAllowedByGameRules

internal fun <T> placeElement(board: Board<T>, coord: Coord, elements: Iterable<T>): Board<T> {
    return board.set(
        coord,
        elements.filter { isAllowedByGameRules(board, coord, it) }
            .randomOrNull() ?: throw RetryException()
    )
}