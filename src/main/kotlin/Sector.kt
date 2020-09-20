import kotlinx.collections.immutable.PersistentSet

data class Sector(
    val sectorCoord: Coord,
    val boardCoords: PersistentSet<Coord>
) {
    override fun toString(): String = "${sectorCoord} -> [${boardCoords.joinToString(", ")}]"
}
