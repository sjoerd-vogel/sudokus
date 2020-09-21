fun <T> Board<T>.getCell(coord: Coord): Cell<T> = cells
    .filter { it.coord == coord }.first()

fun <T> Board<T>.getCellsBySector(sector: Sector) = cells
    .filter { it.coord in sector.coords }

fun <T> Board<T>.getUnfilledSectors() = sectors.filter { sec ->
    sec.coords.any { secc ->
        cells.any { cel -> cel.state == State.Empty && cel.coord == secc }
    }
}

fun <T> Board<T>.getRow(row: Int): Iterable<Cell<T>> = cells
    .filter { it.coord.row == row }

fun <T> Board<T>.getColumn(column: Int): Iterable<Cell<T>> = cells
    .filter { it.coord.column == column }