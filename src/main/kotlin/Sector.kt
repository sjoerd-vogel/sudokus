data class Sector(
    val sectorCoord: Coord,
    val boardCoords: Iterable<Coord>
) {
    override fun toString(): String = "${sectorCoord} -> [${boardCoords.joinToString(", ")}]"
}
