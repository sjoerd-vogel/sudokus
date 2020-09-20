import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet

class SquareNineBoard<T> private constructor(
    val values: PersistentSet<T>,
    val cells: PersistentSet<Cell<T>>
) {
    constructor(values: PersistentSet<T>) : this(values, defaultCells())

    override fun toString(): String = cells.joinToString("\n")

    fun set(coord: Coord, value: T) = SquareNineBoard(
        values,
        cells.map {
            if (it.coord != coord) it
            else Cell(coord, State.Valued(value))
        }.toPersistentSet()
    )

    fun empty(coord: Coord) = SquareNineBoard(
        values,
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

    private companion object {
        private fun <T> defaultCells() = (1..9).selfTensor()
            .map { Cell<T>(it) }
            .toPersistentSet()
    }
}

