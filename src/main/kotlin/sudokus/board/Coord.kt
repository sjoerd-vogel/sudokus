package sudokus.board

data class Coord(val column: Int, val row: Int) {
    val boxColumn: Int = (column - 1) / 3 + 1
    val boxRow: Int = (row - 1) / 3 + 1

    init {
        require(column in (1..9)) { "column $column not in (1..9)" }
        require(row in (1..9)) { "row $row not in (1..9)" }
    }
}
