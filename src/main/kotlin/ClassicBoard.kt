//a classic 9x9, 3x3 sectored sudoku board
class ClassicBoard<T> private constructor(
    val cells: Iterable<Cell<T>>
) {
    constructor() : this(defaultCells())

    override fun toString(): String = cells
        .sortedBy { it.coord.row }
        .groupBy { it.coord.row }
        .values
        .map { row ->
            row.sortedBy { it.coord.column }
                .map { cell -> cell.state }
                .joinToString(" | ", "| ", " |")
        }.joinToString(
            "\n-------------------------------------\n",
            "-------------------------------------\n",
            "\n-------------------------------------"
        )

    val sectors: Iterable<Sector> = (1..3).selfTensor()
        .map { (column, row) -> Coord(column, row) }
        .map { sc ->
            Sector(
                (1..3).selfTensor()
                    .map { (column, row) -> Coord(column, row) }
                    .map { bc -> bc + (sc - Coord(1, 1)) * Coord(3, 3) }
            )
        }

    fun set(coord: Coord, value: T): ClassicBoard<T> = ClassicBoard(
        cells.map {
            if (it.coord != coord) it
            else Cell(coord, State.Valued(value))
        }
    )

    fun getCell(coord: Coord): Cell<T> = cells.filter { it.coord == coord }.first()

    fun getCellsBySector(sector: Sector) = cells.filter { it.coord in sector.coords }

    fun getUnfilledSectors() = sectors.filter { sec ->
        sec.coords.any { secc ->
            cells.any { cel -> cel.state == State.Empty && cel.coord == secc }
        }
    }

    fun getRow(row: Int): Iterable<Cell<T>> = cells.filter { it.coord.row == row }

    fun getColumn(column: Int): Iterable<Cell<T>> = cells.filter { it.coord.column == column }

    private companion object {
        private fun <T> defaultCells(): Iterable<Cell<T>> = (1..9).selfTensor()
            .map { (column, row) -> Cell(Coord(column, row)) }
    }
}

