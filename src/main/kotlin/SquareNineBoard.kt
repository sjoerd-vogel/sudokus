class SquareNineBoard private constructor() {
    private val elements: Set<Int> =
        setOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    private val cells: Map<Pair<Int, Int>, Cell<out Int>> =
        (1..9).flatMap { i -> (1..9).map { j -> Pair(i, j) } }
            .map { p -> Pair(p, Cell(this, p.first, p.second, ConstructionEmptyCell)) }
            .toMap()

    private data class Cell<T>(
        private val board: SquareNineBoard,
        private val row: Int,
        private val column: Int,
        private val state: CellState<T>
    )

    private interface CellState<T>
    private interface ConstructionCellState<T> : CellState<T>

    private object ConstructionEmptyCell : ConstructionCellState<Nothing>
    private data class ConstructionValueCell<T>(val value: T) : ConstructionCellState<T>

    private object EmptyCell : CellState<Nothing>
    private data class FixedCell<T>(val value: T) : CellState<T>
    private data class ValueCell<T>(val value: T) : CellState<T>

    companion object {
        fun getForConstruction(): SquareNineBoard = SquareNineBoard()
    }
}

