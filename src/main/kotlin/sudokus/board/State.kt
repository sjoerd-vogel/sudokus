package sudokus.board

interface State {
    object Empty : State {
        override fun toString(): String = " "
    }

    data class Valued(val value: Int) : State {
        override fun toString(): String = "$value"
    }
}
