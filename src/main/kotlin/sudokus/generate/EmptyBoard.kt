package sudokus.generate

import sudokus.board.Board
import sudokus.board.Cell
import sudokus.board.coords

val emptyBoard: Board = coords.associateWith { Cell() }
