package sudokus.classic.populate

import sudokus.board.Board

internal class RetryException(val board: Board) : RuntimeException()