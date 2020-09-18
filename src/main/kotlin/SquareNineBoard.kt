import SquareNineBoard.State.Valued
import SquareNineBoard.State.Empty
import SquareNineBoard.State.Fixed

class SquareNineBoard {
    private val values: Set<Int> =
            setOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    private val cells: List<Cell<out Int>> =
            (1..9).tensor(1..9)
                    .map { Coord(it.first, it.second) }
                    .map { it.initialCell() }

    override fun toString(): String =
            cells.map { it.toString() }
                    .joinToString("\n")

    private data class Cell<T>(
            private val board: SquareNineBoard,
            private val coord: Coord,
            private val state: State<T>
    ) {

        override public fun toString(): String =
                coord.toString() + " -> " +
                        state.toString()
    }


    private fun Coord.initialCell(): Cell<Int> =
            Cell(this@SquareNineBoard, this@initialCell, Valued(values.first()))

    private data class Coord(val row: Int, val column: Int)

    private interface State<T> {
        object Empty : State<Nothing> {
            override fun toString(): String = "{}"
        }

        data class Fixed<T>(val value: T) : State<T> {
            override fun toString(): String = "{" + value.toString() + "}"
        }

        data class Valued<T>(val value: T) : State<T> {
            override fun toString(): String = "{" + value.toString() + "}"
        }
    }

}

