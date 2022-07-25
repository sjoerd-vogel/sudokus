package sudokus.board

import sudokus.selfTensor

val coords: Set<Coord> = (1..9).selfTensor()
    .map { (row, column) -> Coord(row = row, column = column) }
    .toSet()
