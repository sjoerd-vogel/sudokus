package sudokus.board

//a sudokus.classic 9x9, 3x3 sectored sudoku sudokus.board
data class Board(
    val values: Iterable<Int>,
    val sectors: Iterable<Sector>,
    val cells: Iterable<Cell>
) {
    override fun toString(): String = cells
        .sortedBy { it.coord.row }
        .groupBy { it.coord.row }
        .values
        .map { row ->
            row.sortedBy { it.coord.column }
                .map { cell -> cell.state }
                .joinToString(" | ", "| ", " |")
        }.joinToString(
            "\n-------------------------------------\n",
            "-------------------------------------\n",
            "\n-------------------------------------"
        )

    fun set(coord: Coord, value: Int): Board = Board(
        values,
        sectors,
        cells.map {
            if (it.coord != coord) it
            else Cell(coord, State.Valued(value))
        }
    )

    fun set(cell: Cell): Board = Board(
        values,
        sectors,
        cells.map {
            if (it.coord != cell.coord) it
            else cell
        }
    )

    companion object {
        fun Board.getCellsBySector(sector: Sector) = cells
            .filter { it.coord in sector.coords }
    }

}

