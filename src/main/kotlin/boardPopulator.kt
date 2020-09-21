typealias Board = ClassicBoard<String>

tailrec fun <T> getPopulatedBoard(elements: Iterable<T>): Board {
    val set = elements.map { it.toString() }
    try {
        return _getPopulatedBoard(set)
    } catch (re: RetryException) {
    }
    return getPopulatedBoard(set)
}

private fun _getPopulatedBoard(elements: Iterable<String>): Board {

    tailrec fun populateWorker(board: Board = Board()): Board {
        val empties = board.getEmptyCells()

        return if (empties.none()) board
        else populateWorker(addNextElement(board, elements))
    }
    return populateWorker()
}

private fun <T> Iterable<Cell<T>>.values(): Iterable<T> = this.mapNotNull {
    if (it.state is State.Valued) it.state.value else null
}

private fun filterElements(board: Board, elements: Iterable<String>): Iterable<String> {
    val emptyCoords = board.getEmptyCells()
        .map { it.coord }
    return elements.filter { notInSameRow(board, emptyCoords[0], it) }
        .filter { notInSameColumn(board, emptyCoords[0], it) }
        .filter { notInSameSector(board, emptyCoords[0], it) }
}

private fun addNextElement(board: Board, elements: Iterable<String>): Board {
    val nextCoord = board.getEmptyCells()
        .first()
        .coord
    return board.set(
        nextCoord,
        filterElements(board, elements).toList()
            .randomOrNull() ?: throw RetryException()
    )
}

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

private class RetryException : RuntimeException()