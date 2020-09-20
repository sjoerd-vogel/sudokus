import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet

typealias Board = SquareNineBoard<String>

fun <T> getPopulatedBoard(iterable: Iterable<T>): Board =
    getPopulatedBoard(iterable.toPersistentSet())

tailrec fun <T> getPopulatedBoard(elements: PersistentSet<T>): Board {
    val set = elements.map { it.toString() }.toPersistentSet()
    try {
        return _getPopulatedBoard(set)
    } catch (re: RetryException) {
    }
    return getPopulatedBoard(set)
}

private fun _getPopulatedBoard(elements: PersistentSet<String>): Board {

    tailrec fun populateWorker(board: Board = Board()): Board {
        val empties = board.cells.flatMap {
            if (it.state is State.Empty) listOf(it)
            else emptyList()
        }
        return if (empties.isEmpty()) board
        else populateWorker(addElement(board, empties.first().coord, elements))
    }
    return populateWorker()
}

private fun Iterable<Cell<String>>.values() =
    this.flatMap {
        if (it.state is State.Valued) listOf(it.state.value)
        else emptyList()
    }

fun addElement(board: Board, coord: Coord, elements: PersistentSet<String>): Board =
    board.set(
        coord,
        elements.filter { notInSameRow(board, coord, it) }
            .filter { notInSameColumn(board, coord, it) }
            .filter { notInSameSector(board, coord, it) }
            .randomOrNull() ?: throw RetryException()
    )

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

class RetryException() : RuntimeException()