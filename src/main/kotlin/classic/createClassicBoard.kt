package classic

import board.Board
import board.Cell
import board.Coord
import board.Sector
import selfTensor

//create a classic sudoku board
fun createClassicBoard(): Board<Int> = createClassicBoard(1..9)
fun <T> createClassicBoard(values: Iterable<T>): Board<T> = Board(values, defaultSectors(), defaultCells())

private fun <T> defaultCells(): Iterable<Cell<T>> = (1..9).selfTensor()
    .map { (column, row) -> Cell(Coord(column, row)) }

private fun defaultSectors(): Iterable<Sector> = (1..3).selfTensor()
    .map { (column, row) -> Coord(column, row) }
    .map { sc ->
        Sector(
            (1..3).selfTensor()
                .map { (column, row) -> Coord(column, row) }
                .map { bc -> bc + (sc - Coord(1, 1)) * Coord(3, 3) }
        )
    }

