import kotlinx.collections.immutable.toPersistentList

fun <T, U> Iterable<T>.tensor(that: Iterable<U>) =
    this.flatMap { thisItem -> that.map { thatItem -> Pair(thisItem, thatItem) } }()

fun <T> Iterable<T>.selfTensor() =
    this.tensor(this)

operator fun <T> Iterable<T>.invoke() = this.toPersistentList()
