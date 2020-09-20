data class Coord(val column: Int, val row: Int) {
    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    override fun toString(): String = "(column=${column},row=${row})"
}
