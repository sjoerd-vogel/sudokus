import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

fun <T, U> Iterable<T>.tensor(that: Iterable<U>): PersistentList<Pair<T, U>> =
        this.flatMap { thisItem -> that.map { thatItem -> Pair(thisItem, thatItem) } }
                .toPersistentList()

fun <T> Iterable<T>.selfTensor(): PersistentList<Pair<T, T>> =
        this.tensor(this)