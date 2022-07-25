package sudokus.board

import sudokus.board.houses.rows

private val HSEP = "-----------------------------------------"

fun Board.print(): String = this.rows()
    .asSequence()
    .map { r -> r.map { cc -> " ${cc.value.value?.toString() ?: " "} " } }
    .map { r -> r.chunked(3).map { ch -> ch.joinToString("|", "|", "|") } }
    .map { r -> r.joinToString("", "|", "|") }
    .chunked(3).joinToString("\n", "\n", "\n")
    { ch -> ch.joinToString("\n$HSEP\n", "$HSEP\n", "\n$HSEP") }
