package board

data class Cell(
    val coord: Coord,
    val state: State = State.Empty
) {
    override fun toString(): String = "${coord} -> ${state}"
}
