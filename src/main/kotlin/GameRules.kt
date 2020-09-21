fun allowed(board: Board, coord: Coord, element: String): Boolean =
        notInSameColumn(board, coord, element) &&
        notInSameRow(board, coord, element) &&
        notInSameSector(board, coord, element)

private fun notInSameRow(board: Board, coord: Coord, element: String): Boolean = board.getRow(coord.row)
    .values()
    .none { it == element }

private fun notInSameColumn(board: Board, coord: Coord, element: String): Boolean = board.getColumn(coord.column)
    .values()
    .none { it == element }

private fun notInSameSector(board: Board, coord: Coord, element: String): Boolean = board.sectors
    .first { coord in it.coords }
    .coords
    .map { board.getCell(it) }
    .values()
    .none { it == element }

private fun <T> Iterable<Cell<T>>.values(): Iterable<T> = this.mapNotNull {
    if (it.state is State.Valued) it.state.value else null
}