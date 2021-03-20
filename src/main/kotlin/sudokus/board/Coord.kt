package sudokus.board

data class Coord(val column: Int, val row: Int) {

    operator fun plus(other: Coord) = Coord(
        column = this.column + other.column,
        row = this.row + other.row
    )

    operator fun minus(other: Coord) = Coord(
        column = this.column - other.column,
        row = this.row - other.row
    )

    operator fun times(other: Coord) = Coord(
        column = this.column * other.column,
        row = this.row * other.row
    )

    override fun toString(): String = "(column=$column,row=$row)"
}
