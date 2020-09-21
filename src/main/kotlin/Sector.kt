import kotlinx.collections.immutable.PersistentList

data class Sector(
    val sectorCoord: Coord,
    val boardCoords: PersistentList<Coord>
) {
    override fun toString(): String = "${sectorCoord} -> [${boardCoords.joinToString(", ")}]"
}
