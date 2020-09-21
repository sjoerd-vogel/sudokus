package com.vogel.sjoerd.sudokus.classic.populate

import com.vogel.sjoerd.sudokus.board.Board
import com.vogel.sjoerd.sudokus.board.Coord
import com.vogel.sjoerd.sudokus.classic.IsAllowedByGameRules

internal object PlaceElement {
    internal operator fun <T> invoke(board: Board<T>, coord: Coord, elements: Iterable<T>): Board<T> {
        return board.set(
            coord,
            elements.filter { IsAllowedByGameRules(board, coord, it) }
                .randomOrNull() ?: throw RetryException()
        )
    }
}