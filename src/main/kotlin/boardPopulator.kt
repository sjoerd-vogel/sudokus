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

private fun addElement(board: Board, coord: Coord, elements: Iterable<String>): Board {
    return board.set(
        coord,
        elements.filter { allowed(board, coord, it) }
            .randomOrNull() ?: throw RetryException()
    )
}


private class RetryException : RuntimeException()