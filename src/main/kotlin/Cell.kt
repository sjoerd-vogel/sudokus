data class Cell<T>(
    val coord: Coord,
    val state: State<out T> = State.Empty
) {
    constructor(pair: Pair<Int, Int>) : this(Coord(pair))

    override fun toString(): String = "${coord} -> ${state}"
}
