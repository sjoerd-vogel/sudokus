package sudokus.board

import kotlin.math.max
import kotlin.math.min

fun Board.rectangle(first: Coord, second: Coord): Map<Coord, Cell> =
    filterKeys { min(first.row, second.row) <= it.row && it.row <= max(first.row, second.row) }
        .filterKeys { min(first.column, second.column) <= it.column && it.column <= max(first.column, second.column) }
