package external.iterable

//combine two iterables into an iterable of pairs of combinations while preserving order
// order first on second entry, then on the first, ie (1,1), (1,2)...
// this operation and name is inspired from https://en.wikipedia.org/wiki/Tensor_product
// suggestions for better implementation/naming are welcome

fun <T, U> Iterable<T>.tensor(that: Iterable<U>): Iterable<Pair<T, U>> = flatMap { thisItem ->
    that.map { thatItem -> Pair(thisItem, thatItem) }
}

fun <T> Iterable<T>.selfTensor(): Iterable<Pair<T, T>> = tensor(this)

