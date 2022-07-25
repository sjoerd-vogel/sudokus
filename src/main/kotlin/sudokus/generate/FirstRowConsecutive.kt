package sudokus.generate

import sudokus.board.Board
import sudokus.board.Cell
import sudokus.board.coords
import sudokus.board.set

//fill first box consecutive
val withFirstRowConsecutive: Board = coords.filter { it.row == 1 }
    .foldIndexed(emptyBoard) { i, a, b -> a.set(b, Cell(i + 1)) }
