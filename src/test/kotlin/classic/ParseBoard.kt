package classic

import board.Board
import board.Cell
import board.Coord
import board.State
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private fun String.parse(): Board<String> =
    replace("\n", "")
        .replace("-", "")
        .replace("||", "|")
        .removeSuffix("|")
        .chunked(4)
        .map { it.get(2) }
        .map(Any::toString)
        .chunked(9)
        .flatMapIndexed { row, list ->
            list.mapIndexed { column, string ->
                if (string == " ") Cell<String>(Coord(row = row + 1, column = column + 1), State.Empty)
                else Cell(Coord(row = row + 1, column = column + 1), State.Valued(string))
            }
        }.fold(createClassicBoard(), { board, cell -> board.set(cell) })

class ParseTests {
    @Test
    fun parse() {
        val str = listOf(
            "-------------------------------------\n",
            "| 5 | 1 | 7 | 3 | 2 | 6 | 9 | 4 | 8 |\n",
            "-------------------------------------\n",
            "| 4 | 2 | 9 | 5 | 8 | 7 | 3 | 1 | 6 |\n",
            "-------------------------------------\n",
            "| 8 | 6 | 3 | 1 | 9 | 4 | 2 | 7 | 5 |\n",
            "-------------------------------------\n",
            "| 7 | 4 | 5 | 8 | 3 | 2 | 1 | 6 | 9 |\n",
            "-------------------------------------\n",
            "| 1 | 8 | 6 | 4 | 5 | 9 | 7 | 3 | 2 |\n",
            "-------------------------------------\n",
            "| 9 | 3 | 2 | 6 | 7 | 1 | 8 | 5 | 4 |\n",
            "-------------------------------------\n",
            "| 2 | 9 | 4 | 7 | 1 | 5 | 6 | 8 | 3 |\n",
            "-------------------------------------\n",
            "| 6 | 7 | 8 | 9 | 4 | 3 | 5 | 2 | 1 |\n",
            "-------------------------------------\n",
            "| 3 | 5 | 1 | 2 | 6 | 8 | 4 | 9 | 7 |\n",
            "-------------------------------------"
        ).joinToString("", "", "")

        assertEquals(str.parse().toString(), str)
        assertEquals(
            str
                .replace("\n", "")
                .parse().toString(),
            str
        )

    }

}