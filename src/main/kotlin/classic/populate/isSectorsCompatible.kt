package classic.populate

import board.Board
import board.Board.Companion.getCellsBySector
import board.Coord
import board.Sector
import board.State
import selfTensor
import tensor

fun sectorCompatible(board: Board, coord: Coord, sector: Sector, value: Int): Boolean {
    val updated = board.set(coord, value)
    return updated.sameRowSectors(sector).none { areSectorsHorizontalCompatible(updated, sector, it) } &&
            updated.sameColumnSectors(sector).none { areSectorsVerticalCompatible(updated, sector, it) }
}

internal fun areSectorsHorizontalCompatible(board: Board, first: Sector, second: Sector): Boolean {
    val joinedFirstRowPairs = board.getCellsBySector(first)
        .groupBy { it.coord.row }
        .values
        .map { row ->
            row.mapNotNull { cell ->
                if (cell.state is State.Valued) cell.state.value
                else null
            }
        }.selfTensor()
        .filterNot { (first, second) -> first == second }
        .map { (first, second) -> first + second }

    val joinedSecondRowPairs = board.getCellsBySector(second)
        .groupBy { it.coord.row }
        .values
        .map { row ->
            row.mapNotNull { cell ->
                if (cell.state is State.Valued) cell.state.value
                else null
            }
        }.selfTensor()
        .filterNot { (first, second) -> first == second }
        .map { (first, second) -> first + second }

    return (joinedFirstRowPairs.tensor(joinedSecondRowPairs)
        .map { (first, second) -> first.count { firstel -> firstel in second } }
        .maxOrNull() ?: 0) < 4
}

internal fun  areSectorsVerticalCompatible(board: Board, first: Sector, second: Sector): Boolean {
    val joinedFirstRowPairs = board.getCellsBySector(first)
        .groupBy { it.coord.column }
        .values
        .map { column ->
            column.mapNotNull { cell ->
                if (cell.state is State.Valued) cell.state.value
                else null
            }
        }.selfTensor()
        .filterNot { (first, second) -> first == second }
        .map { (first, second) -> first + second }

    val joinedSecondRowPairs = board.getCellsBySector(second)
        .groupBy { it.coord.column }
        .values
        .map { column ->
            column.mapNotNull { cell ->
                if (cell.state is State.Valued) cell.state.value
                else null
            }
        }.selfTensor()
        .filterNot { (first, second) -> first == second }
        .map { (first, second) -> first + second }

    return (joinedFirstRowPairs.tensor(joinedSecondRowPairs)
        .map { (first, second) -> first.count { firstel -> firstel in second } }
        .maxOrNull() ?: 0) < 4
}

internal fun  Board.sameRowSectors(sector: Sector) = sectors.filter { sec ->
    sec.coords.any { boardCoord ->
        boardCoord.row in sector.coords.map { coord ->
            coord.row
        }
    }
}.filterNot { it == sector }

internal fun  Board.sameColumnSectors(sector: Sector) = sectors.filter { sec ->
    sec.coords.any { boardCoord ->
        boardCoord.column in sector.coords.map { coord ->
            coord.column
        }
    }
}.filterNot { it == sector }

