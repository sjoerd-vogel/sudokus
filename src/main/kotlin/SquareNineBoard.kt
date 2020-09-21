import kotlinx.collections.immutable.toPersistentList

class SquareNineBoard<T> private constructor(
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

    val sectors = (1..3).selfTensor()
        .map { (column, row) -> Coord(column, row) }
        .map { sc ->
            Sector(
                sc,
                (1..3).selfTensor()
                    .map { (column, row) -> Coord(column, row) }
                    .map { bc -> bc + (sc - Coord(1, 1)) * Coord(3, 3) }
                    .toPersistentList()
            )
        }
        .toPersistentList()

    fun set(coord: Coord, value: T) = SquareNineBoard(
        cells.map {
            if (it.coord != coord) it
            else Cell(coord, State.Valued(value))
        }.toPersistentList()
    )

    fun empty(coord: Coord) = SquareNineBoard(
        cells.map {
            if (it.coord != coord) it
            else Cell<T>(coord, State.Empty)
        }
    )

    fun getCell(coord: Coord): Cell<T> = cells.filter { it.coord == coord }
        .first()

    fun getEmptyCells() = cells.flatMap {
        if (it.state is State.Empty) listOf(it)
        else emptyList()
    }.toPersistentList()

    fun getRow(row: Int) = cells.filter { it.coord.row == row }
        .toPersistentList()

    fun getColumn(column: Int) = cells.filter { it.coord.column == column }
        .toPersistentList()

    fun getSector(coord: Coord) = sectors.filter { it.sectorCoord == coord }
        .map { sec -> cells.filter { cell -> cell.coord in sec.boardCoords } }
        .toPersistentList()

    private companion object {
        private fun <T> defaultCells() = (1..9).selfTensor()
            .map { (column, row) -> Cell<T>(Coord(column, row)) }
            .toPersistentList()
    }
}

