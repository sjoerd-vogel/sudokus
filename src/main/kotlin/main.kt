fun main() {
    (1..9).tensor(1..9)
            .map { it.toString() }
            .forEach { it.println() }
    "------------------".println()
    SquareNineBoard().toString().println()
}