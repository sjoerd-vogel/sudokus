import kotlinx.collections.immutable.toPersistentList

fun <T, U> Iterable<T>.tensor(that: Iterable<U>) =
    this.flatMap { thisItem -> that.map { thatItem -> Pair(thisItem, thatItem) } }
        .toPersistentList()

fun <T> Iterable<T>.selfTensor() =
    this.tensor(this)