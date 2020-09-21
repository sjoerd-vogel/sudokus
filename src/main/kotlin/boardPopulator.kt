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

    tailrec fun worker(board: Board = Board()): Board {
        val empties = board.getUnfilledSectors()

        return if (empties.none()) board
        else worker(fillSector(board, empties.first(), elements))
    }
    return worker()
}

private fun fillSector(board: Board, sector: Sector, elements: Iterable<String>): Board {
    tailrec fun worker(board: Board): Board {
        val empties = board.getCellsBySector(sector)
            .filter { it.state == State.Empty }

        return if (empties.none()) board
        else worker(addElement(board, empties.first().coord, elements))
    }
    return worker(board)
}

private fun <T> Iterable<Cell<T>>.values(): Iterable<T> = this.mapNotNull {
    if (it.state is State.Valued) it.state.value else null
}

private fun filterElements(board: Board, coord: Coord, elements: Iterable<String>): Iterable<String> {
    return elements.filter { notInSameRow(board, coord, it) }
        .filter { notInSameColumn(board, coord, it) }
        .filter { notInSameSector(board, coord, it) }
}

private fun addElement(board: Board, coord: Coord, elements: Iterable<String>): Board {
    return board.set(
        coord,
        filterElements(board, coord, elements).toList()
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