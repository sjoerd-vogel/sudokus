package sudokus.board

fun Board.set(coord: Coord, cell: Cell): Board = this.map { (k, v) ->
    if (k == coord) Pair(k, cell)
    else Pair(k, v)
}.toMap()
