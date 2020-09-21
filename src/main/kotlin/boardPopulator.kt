//add elements to cells of classic board
tailrec fun <T> getPopulatedBoard(elements: Iterable<T>): Board<T> {
    try {
        return _getPopulatedBoard(elements)
    } catch (re: RetryException) {
    }
    return getPopulatedBoard(elements)
}

private fun <T> _getPopulatedBoard(elements: Iterable<T>): Board<T> {

    tailrec fun worker(board: Board<T> = createClassicBoard()): Board<T> {
        val empties = board.getUnfilledSectors()

        return if (empties.none()) board
        else worker(fillSector(board, empties.first(), elements))
    }
    return worker()
}

private fun <T> fillSector(board: Board<T>, sector: Sector, elements: Iterable<T>): Board<T> {
    tailrec fun worker(board: Board<T>): Board<T> {
        val empties = board.getCellsBySector(sector)
            .filter { it.state == State.Empty }

        return if (empties.none()) board
        else worker(addElement(board, empties.first().coord, elements))
    }
    return worker(board)
}

private fun <T> addElement(board: Board<T>, coord: Coord, elements: Iterable<T>): Board<T> {
    return board.set(
        coord,
        elements.filter { allowed(board, coord, it) }
            .randomOrNull() ?: throw RetryException()
    )
}


private class RetryException : RuntimeException()