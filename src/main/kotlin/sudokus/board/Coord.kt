package sudokus.board

data class Coord(val column: Int, val row: Int) {
    init {
        require(column in (1..9)) { "column $column not in (1..9)" }
        require(row in (1..9)) { "row $row not in (1..9)" }
    }
}
