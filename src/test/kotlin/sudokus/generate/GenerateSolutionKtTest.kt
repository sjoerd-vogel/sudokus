package sudokus.generate

import org.junit.jupiter.api.Test
import sudokus.board.print

private class GenerateSolutionKtTest {
    @Test
    fun bla() {
        val x = 1
        val bla = (1..x).map { solutionWithAttempts() }
        println(bla[0].first.print())
        println(bla[0].second.getOrNull(0)?.print())
        println(bla.sumOf { it.second.size } / x)
    }
}
