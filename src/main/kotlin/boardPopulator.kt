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

fun filterElements(board: Board, elements: Iterable<String>): Iterable<String> {
    val emptyCoords = board.getEmptyCells()
        .map { it.coord }
        .toPersistentList()
    return elements.filter { notInSameRow(board, emptyCoords[0], it) }
        .filter { notInSameColumn(board, emptyCoords[0], it) }
        .filter { notInSameSector(board, emptyCoords[0], it) }
        .toPersistentList()
}

fun elementDoesNotBlock(board: Board, element: String, elements: Iterable<String>): Boolean {
    val emptyCoords = board.getEmptyCells()
        .map { it.coord }
        .toPersistentList()
    if (emptyCoords.size < 2) return true
    val boardCandidate: Board = board.set(
        emptyCoords[0],
        element
    )
    return elements.filter { notInSameRow(boardCandidate, emptyCoords[1], it) }
        .filter { notInSameColumn(boardCandidate, emptyCoords[1], it) }
        .filter { notInSameSector(boardCandidate, emptyCoords[1], it) }
        .isNotEmpty()
}


fun addNextElement(board: Board, elements: Iterable<String>): Board {
    val nextCoord = board.getEmptyCells()
        .first()
        .coord
    return board.set(
        nextCoord,
        filterElements(board, elements)
            .filter { elementDoesNotBlock(board, it, elements) }
            .randomOrNull() ?: throw RetryException()
    )
}

fun notInSameRow(board: Board, coord: Coord, element: String) = board.getRow(coord.row)
    .values()
    .none { it == element }

fun notInSameColumn(board: Board, coord: Coord, element: String) = board.getColumn(coord.column)
    .values()
    .none { it == element }

fun notInSameSector(board: Board, coord: Coord, element: String) = board.sectors
    .first { coord in it.boardCoords }
    .boardCoords
    .map { board.getCell(it) }
    .values()
    .none { it == element }

class RetryException : RuntimeException()