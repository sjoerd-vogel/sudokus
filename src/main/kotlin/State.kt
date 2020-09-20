interface State<T> {
    object Empty : State<Nothing> {
        override fun toString(): String = " "
    }

    data class Valued<T>(val value: T) : State<T> {
        override fun toString(): String = "${value}"
    }
}
