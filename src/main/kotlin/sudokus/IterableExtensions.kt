package sudokus

fun <T, U> Iterable<T>.tensor(that: Iterable<U>): Iterable<Pair<T, U>> = flatMap { thisItem ->
    that.map { thatItem -> Pair(thisItem, thatItem) }
}

fun <T> Iterable<T>.selfTensor(): Iterable<Pair<T, T>> = tensor(this)

fun Iterable<Boolean>.allTrue() = this.fold(true) { a, b -> a && b }
fun Iterable<Boolean>.allFalse() = !this.fold(false) { a, b -> a || b }
