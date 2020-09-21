package com.vogel.sjoerd.sudokus.board

//a classic 9x9, 3x3 sectored sudoku board
data class Board<T>(
    val sectors: Iterable<Sector>,
    val cells: Iterable<Cell<T>>
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

    fun set(coord: Coord, value: T): Board<T> = Board(
        sectors,
        cells.map {
            if (it.coord != coord) it
            else Cell(coord, State.Valued(value))
        }
    )

}

