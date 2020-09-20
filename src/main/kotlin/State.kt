interface State<T> {
    object Empty : State<Nothing> {
        override fun toString(): String = "{}"
    }

    // for future use
//    data class Fixed<T>(val value: T) : State<T> {
//        override fun toString(): String = "{${value}}"
//    }

    data class Valued<T>(val value: T) : State<T> {
        override fun toString(): String = "{${value}}"
    }
}
