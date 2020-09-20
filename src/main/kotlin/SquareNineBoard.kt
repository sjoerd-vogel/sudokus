import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet

class SquareNineBoard<T> private constructor(
    val cells: PersistentSet<Cell<T>>
) {
    constructor() : this(defaultCells())

    override fun toString(): String = cells.joinToString("\n")

    val sectors: PersistentSet<Sector> = (1..3).selfTensor()
        .map { Coord(it) }
        .map { sc ->
            Sector(
                sc,
                (1..3).selfTensor()
                    .map { bc -> Coord(bc) }
                    .map { bc -> bc + (sc - Coord(1, 1)) * Coord(3, 3) }
                    .toPersistentSet()
            )
        }
        .toPersistentSet()

    fun set(coord: Coord, value: T) = SquareNineBoard(
        cells.map {
            if (it.coord != coord) it
            else Cell(coord, State.Valued(value))
        }.toPersistentSet()
    )

    fun empty(coord: Coord) = SquareNineBoard(
        cells.map {
            if (it.coord != coord) it
            else Cell<T>(coord, State.Empty)
        }.toPersistentSet()
    )

    fun getCell(coord: Coord): Cell<T> = cells.filter { it.coord == coord }
        .first()

    fun getRow(row: Int): PersistentSet<Cell<T>> = cells.filter { it.coord.row == row }
        .toPersistentSet()

    fun getColumn(column: Int): PersistentSet<Cell<T>> = cells.filter { it.coord.column == column }
        .toPersistentSet()

    fun getSector(coord: Coord) = sectors.filter { it.sectorCoord == coord }
        .map { sec -> cells.filter { cell -> cell.coord in sec.boardCoords } }
        .toPersistentSet()

    private companion object {
        private fun <T> defaultCells() = (1..9).selfTensor()
            .map { Cell<T>(it) }
            .toPersistentSet()
    }
}

