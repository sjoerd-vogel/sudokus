package sudokus.classic

import sudokus.board.Board
import sudokus.board.Cell
import sudokus.board.Coord
import sudokus.board.Sector
import selfTensor

//create a sudokus.classic sudoku sudokus.board
fun createClassicBoard(): Board = createClassicBoard(1..9)
private fun createClassicBoard(values: Iterable<Int>): Board = Board(values, defaultSectors(), defaultCells())

private fun defaultCells(): Iterable<Cell> = (1..9).selfTensor()
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

