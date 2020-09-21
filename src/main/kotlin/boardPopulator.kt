import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

typealias Board = SquareNineBoard<String>

tailrec fun <T> getPopulatedBoard(elements: Iterable<T>): Board {
    val set = elements.map { it.toString() }.toPersistentList()
    try {
        return _getPopulatedBoard(set)
    } catch (re: RetryException) {
    }
    return getPopulatedBoard(set)
}

private fun _getPopulatedBoard(elements: Iterable<String>): Board {

    tailrec fun populateWorker(board: Board = Board()): Board {
        val empties = board.getEmptyCells()

        return if (empties.isEmpty()) board
        else populateWorker(addNextElement(board, elements))
    }
    return populateWorker()
}

private fun Iterable<Cell<String>>.values() =
    this.flatMap {
        if (it.state is State.Valued) listOf(it.state.value)
        else emptyList()
    }

private fun filterElements(board: Board, elements: Iterable<String>): PersistentList<String> {
    val emptyCoords = board.getEmptyCells()
        .map { it.coord }
        .toPersistentList()
    return elements.filter { notInSameRow(board, emptyCoords[0], it) }
        .filter { notInSameColumn(board, emptyCoords[0], it) }
        .filter { notInSameSector(board, emptyCoords[0], it) }
        .toPersistentList()
}

private fun addNextElement(board: Board, elements: Iterable<String>): Board {
    val nextCoord = board.getEmptyCells()
        .first()
        .coord
    return board.set(
        nextCoord,
        filterElements(board, elements)
            .toPersistentList()
            .randomOrNull() ?: throw RetryException()
    )
}

private fun notInSameRow(board: Board, coord: Coord, element: String) = board.getRow(coord.row)
    .values()
    .none { it == element }

private fun notInSameColumn(board: Board, coord: Coord, element: String) = board.getColumn(coord.column)
    .values()
    .none { it == element }

private fun notInSameSector(board: Board, coord: Coord, element: String) = board.sectors
    .first { coord in it.boardCoords }
    .boardCoords
    .map { board.getCell(it) }
    .values()
    .none { it == element }

private class RetryException : RuntimeException()