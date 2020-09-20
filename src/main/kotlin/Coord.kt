data class Coord(val column: Int, val row: Int) {
    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

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

    override fun toString(): String = "(column=${column},row=${row})"
}
