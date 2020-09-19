import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList

class SquareNineBoard<T> private constructor(
    private val values: PersistentSet<T>,
    private val cells: PersistentList<Cell<T>>
) {
    constructor(values: PersistentSet<T>) : this(values, defaultCells())

    override fun toString(): String = cells.map { it.toString() }
        .joinToString("\n")

    private data class Cell<T>(
        private val coord: Coord,
        private val state: State<out T> = State.Empty
    ) {
        constructor(pair: Pair<Int, Int>) : this(Coord(pair))

        override fun toString(): String = "${coord} -> ${state}"
    }


    private data class Coord(val column: Int, val row: Int) {
        constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

        override fun toString(): String = "(column=${column},row=${row})"
    }

    private interface State<T> {
        object Empty : State<Nothing> {
            override fun toString(): String = "{}"
        }

        data class Fixed<T>(val value: T) : State<T> {
            override fun toString(): String = "{${value}}"
        }

        data class Valued<T>(val value: T) : State<T> {
            override fun toString(): String = "{${value}}"
        }
    }

    private companion object {
        private fun <T> defaultCells() = (1..9).selfTensor()
            .map { Cell<T>(it) }
            .toPersistentList()
    }
}

