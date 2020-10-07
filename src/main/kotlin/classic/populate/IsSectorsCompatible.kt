package classic.populate

import board.Board
import board.Coord
import board.Sector
import board.State
import external.iterable.tensor

fun <T> sectorCompatible(board: Board<T>): Boolean = true

private fun Iterable<Sector>.isRowCompatible(): Boolean = false
//    selfTensor()

private fun <T> Sector.rowCompatible(other: Sector, board: Board<T>) : Boolean =
    if (this == other) true
    else if (coords.none { tc -> tc.row in other.coords.map { oc -> oc.row } }) true
    else bla(coords, other, board)

private fun <T> bla(coords : Iterable<Coord>, other:Sector, board: Board<T>) : Boolean=
    coords.groupBy { it.row }
        .toList()
        .tensor(other.coords.groupBy { it.row }.toList())
        .filterNot { (pa, pb) -> pa.first == pb.first }
        .map { (pa, pb) -> Pair(pa.second, pb.second) }
        .map { (la, lb) -> Pair(la.map { ca -> board.get(ca) }, lb.map { cb -> board.get(cb) }) }
        .none { (la, lb) -> la.intersect(lb).size > 4 }

private fun Iterable<Sector>.rows(): Iterable<Iterable<Sector>> =
    groupBy { ss -> ss.coords.minByOrNull { cs -> cs.row }!! }.values

private fun Iterable<Sector>.columns(): Iterable<Iterable<Sector>> =
    groupBy { ss -> ss.coords.minByOrNull { cs -> cs.column }!! }.values

private fun <T> Board<T>.get(coord: Coord): State<out T> = cells.first { it.coord == coord }.state