fun <T, U> Iterable<T>.tensor(that: Iterable<U>): List<Pair<T, U>> =
        this.flatMap { thisItem -> that.map { thatItem -> Pair(thisItem, thatItem) } }

fun <T> Iterable<T>.selfTensor(): List<Pair<T, T>> =
        this.tensor(this)

fun String.println() = println(this)
fun String.print() = print(this)
