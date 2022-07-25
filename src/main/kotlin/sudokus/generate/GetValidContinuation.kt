package sudokus.generate

import sudokus.board.Board
import sudokus.board.Cell
import sudokus.board.Coord
import sudokus.board.houses.boxes
import sudokus.board.houses.columns
import sudokus.board.houses.rows
import sudokus.board.set

fun Board.getValidContinuations(coord: Coord, values: Set<Int>): List<Pair<Board, Int>> {
    return values.map { Pair(this.set(coord, Cell(it)), it) }
        .filter { (b, _) -> satisfiesRules(b) }
}

fun satisfiesRules(board: Board): Boolean =
    board.rows().fold(true) { bl, r ->
        bl && r.values.mapNotNull { it.value }
            .groupingBy { it }.eachCount().all { it.value <= 1 }
    }
            &&
            board.columns().fold(true) { bl, c ->
                bl && c.values.mapNotNull { it.value }
                    .groupingBy { it }.eachCount().all { it.value <= 1 }
            }
            &&
            board.boxes().fold(true) { bl, bx ->
                bl && bx.values.mapNotNull { it.value }
                    .groupingBy { it }.eachCount().all { it.value <= 1 }
            }
