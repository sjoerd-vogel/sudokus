data class Sector(
    val coords: Iterable<Coord>
) {
    override fun toString(): String = "[${coords.joinToString(", ")}]"
}
