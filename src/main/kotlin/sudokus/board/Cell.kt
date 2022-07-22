package sudokus.board

data class Cell(val value: Int? = null, val fixed: Boolean = false) {
    init {
        if (value != null) require(value in (1..9)) { "value $value is not valid" }
        if (fixed) require(value != null) { "value cannot be null for fixed cells" }
    }
}
