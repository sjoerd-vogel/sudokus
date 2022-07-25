package sudokus.board.houses

import sudokus.board.Board
import sudokus.board.Cell
import sudokus.board.Coord
import sudokus.selfTensor

private val boxCoords = (1..3).selfTensor()

fun Board.boxes(): List<Map<Coord, Cell>> =
    boxCoords.map { (column, row) ->
        this.filterKeys { k -> k.boxColumn == column && k.boxRow == row }
    }
