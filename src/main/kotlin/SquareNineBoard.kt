import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList

class SquareNineBoard {
    private val values = persistentSetOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    private val cells: PersistentList<Cell<Int>> = (1..9).selfTensor()
        .map { Cell<Int>(it) }
        .toPersistentList()

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

}

