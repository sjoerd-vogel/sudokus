package sudokus.generate

import sudokus.board.*
import sudokus.board.houses.columns
import sudokus.combinations
import sudokus.selfCombinations

//generate a solution with a consecutive first Row, change to random 'basis' later
fun solutionWithAttempts(): Pair<Board, List<Board>> {
    tailrec fun loop(wrapper: Wrapper = solution(), list: List<Board> = listOf()): Pair<Board, List<Board>> {
        return if (!wrapper.solvable) loop(list = list + wrapper.board)
        else Pair(wrapper.board, list)
    }
    return loop()
}

fun solution(): Wrapper {
    val boardWithThirdRow = boardWithThirdRow()
    val withFirstTriple = boardWithThirdRow
        .fill(coords.filter { it.row == 4 && it.column < 4 }.toSet())
        .also { require(it.solvable) }.board
    val firstTriple = withFirstTriple.filterKeys { it.row == 4 }.values.mapNotNull { it.value }
        .toSet()
    val remainder = (1..9) - firstTriple

    val lastThreeColumnRemainders = withFirstTriple.columns()
        .filter { cm -> cm.keys.any { co -> co.column > 6 } }
        .map { cm ->
            val x = cm.values.mapNotNull { cl -> cl.value } - firstTriple
            x + remainder.shuffled().take(3 - x.size)
        }

    val combs = lastThreeColumnRemainders.combinations().shuffled()
    val withSecondTripple = combs.asSequence()
        .flatMap { withFirstTriple.withSafeTriples(it).toList().shuffled() }
        .first()

    val finalFourTriple = ((1..9) - withSecondTripple.filterKeys { it.row == 4 }.values.mapNotNull { it.value }.toSet())
    val four = withSecondTripple.withSafeTriple(finalFourTriple)

    val deduced = four

    //do the rest
    return deduced
        .fill(deduced.filterValues { it.value == null }.keys)

}

private fun Board.withSafeTriple(vals: Iterable<Int>): Board =
    this.withSafeTriple(this.filterValues { it.value == null }.keys.take(3), vals)

private fun Board.withSafeTriples(vals: Iterable<Int>): Sequence<Board> =
    this.withSafeTriples(this.filterValues { it.value == null }.keys.take(3), vals)

private fun Board.withSafeTriple(coords: Iterable<Coord>, vals: Iterable<Int>): Board =
    this.withSafeTriples(coords, vals).toList().random()

private fun Board.withSafeTriples(coords: Iterable<Coord>, vals: Iterable<Int>): Sequence<Board> {
    require(coords.count() == 3)
    require(vals.count() == 3)
    require(coords.all { it.boxRow == coords.first().boxRow && it.boxColumn == coords.first().boxColumn })
    require(coords.all { it.row == coords.first().row })

    return vals.selfCombinations()
        .asSequence()
        .map { it.zip(coords) }
        .map { it.fold(this) { acc, (i, c) -> acc.set(c, Cell(i)) } }
        .filter { satisfiesRules(it) }
}

private fun boardWithThirdRow(): Board =
    boardWithSecondRow()
        .fill(coords.filter { it.row == 3 }.toSet())
        .also { require(it.solvable) }.board

private fun boardWithSecondRow(): Board {
    val higherRowCoords = coords.filter { it.row > 1 }.toSet()
    val firstTripleCoords = higherRowCoords.filter { it.row == 2 && it.column <= 3 }.toSet()
    val secondTripleCoords = higherRowCoords.filter { it.row == 2 && 4 <= it.column && it.column <= 6 }.toSet()

    val withFirstTriple = withFirstRowConsecutive
        .fill(firstTripleCoords)
        .also { require(it.solvable) }.board
    val firstTriple = withFirstTriple.rectangle(Coord(column = 1, row = 2), Coord(column = 3, row = 2))
        .values.mapNotNull { it.value }.toSet()

    val mandatoryValues = ((7..9) - firstTriple.toSet()).toSet()
    val secondTriple =
        (mandatoryValues + ((((1..3) - firstTriple.toSet()).shuffled().take(3 - mandatoryValues.size))))

    return withFirstTriple.fill(secondTripleCoords, secondTriple)
        .also { require(it.solvable) }.board
        .fill(coords.filter { it.column > 6 && it.row == 2 }.toSet())
        .also { require(it.solvable) }.board
}

private fun Board.fill(coords: Set<Coord>, values: Set<Int> = (1..9).toSet()): Wrapper =
    coords.fold(Wrapper(this)) { w, c ->
        if (!w.solvable) {
            w
        } else {
            w.board.getValidContinuations(coord = c, values = values)
                .shuffled()
                .firstOrNull()
                ?.let { Wrapper(it.first) } ?: Wrapper(board = w.board, solvable = false)
        }
    }

data class Wrapper(val board: Board, val solvable: Boolean = true)
