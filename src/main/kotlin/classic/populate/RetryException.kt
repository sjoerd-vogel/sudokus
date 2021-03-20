package classic.populate

import board.Board

internal class RetryException(val board: Board) : RuntimeException()