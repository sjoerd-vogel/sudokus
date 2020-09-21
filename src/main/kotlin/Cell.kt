data class Cell<T>(
    val coord: Coord,
    val state: State<out T> = State.Empty
) {
    override fun toString(): String = "${coord} -> ${state}"
}
