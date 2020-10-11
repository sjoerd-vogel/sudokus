package classic.populate

import board.Board
import board.Coord
import board.Sector
import board.State
import external.iterable.tensor

fun <T> sectorCompatible(board: Board<T>): Boolean = board.sectors.exclusiveSelfTensor()
    .all { (sa, sb) -> sa.rowCompatible(sb, board) } &&
        board.sectors.exclusiveSelfTensor()
            .all { (sa, sb) -> sa.columnCompatible(sb, board) }

private fun <T> Iterable<Sector>.areColumnCompatible(board: Board<T>): Boolean = exclusiveSelfTensor()
    .all { (first, second) -> first.columnCompatible(second, board) }

private fun <T> Iterable<Sector>.areRowCompatible(board: Board<T>): Boolean = exclusiveSelfTensor()
    .all { (first, second) -> first.rowCompatible(second, board) }

private fun <T> Sector.rowCompatible(other: Sector, board: Board<T>): Boolean =
    coordCompatible(other, board) { it.row }

private fun <T> Sector.columnCompatible(other: Sector, board: Board<T>): Boolean =
    coordCompatible(other, board) { it.column }

private fun <T> Sector.coordCompatible(other: Sector, board: Board<T>, coordProp: (Coord) -> Int): Boolean =
    if (this == other) true
    else if (coords.none { tc -> coordProp(tc) in other.coords.map(coordProp) }) true
    else if (this.isFull(board) && other.isFullLike(board)) true
    else if (this.isFullLike(board) && other.isFull(board)) true
    else coords.groupBy(coordProp)
        .values
        .tensor(other.coords.groupBy(coordProp).values)
        .map { (llist, rlist) ->
            Pair(llist.values(board), rlist.values(board))
        }
        .map { (rlist, llist) -> rlist.count { ca -> ca in llist } }
        .sum() < 4

private fun <T> Sector.isFull(board: Board<T>): Boolean {
    val vals = coords.map { board.get(it) }
        .filterNot { it is State.Empty }
        return vals.size > 8
}

private fun <T> Sector.isFullLike(board: Board<T>): Boolean {
    val vals = coords.map { board.get(it) }
        .filterNot { it is State.Empty }
        return vals.size > 5
}

private fun <T> List<Coord>.values(board: Board<T>) = mapNotNull { coord ->
    if (board.get(coord) is State.Empty) null
    else board.get(coord)
}

private fun <T> Board<T>.get(coord: Coord): State<out T> = cells.first { it.coord == coord }.state

private fun <T> Iterable<T>.exclusiveSelfTensor(): Iterable<Pair<T, T>> = flatMap { thisItem ->
    this.map { thatItem -> Pair(thisItem, thatItem) }
}

