package sudokus.board

import sudokus.selfTensor

val coords: List<Coord> = (1..9).selfTensor()
    .map { (row, column) -> Coord(row = row, column = column) }
