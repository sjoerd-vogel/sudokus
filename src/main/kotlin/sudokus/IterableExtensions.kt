package sudokus

fun <T, U> Iterable<T>.tensor(that: Iterable<U>): Iterable<Pair<T, U>> = flatMap { thisItem ->
    that.map { thatItem -> Pair(thisItem, thatItem) }
}

fun <T> Iterable<T>.selfTensor(): Iterable<Pair<T, T>> = tensor(this)

fun Iterable<Boolean>.allTrue() = this.fold(true) { a, b -> a && b }
fun Iterable<Boolean>.allFalse() = !this.fold(false) { a, b -> a || b }

fun <T> Iterable<T>.containsDuplicates() = this.groupBy { it }
    .toList()
    .fold(false) { b, v -> b || v.second.count() > 1 }

fun Iterable<Int>.multiply(): Int = fold(1) { i, j -> i * j }

fun <T : Any> Iterable<T>.selfCombinations(): Sequence<List<T>> =
    List(this.count()) { this }
        .combinations()

fun <T : Any, V : Iterable<T>> Iterable<V>.combinations(): Sequence<List<T>> {
    var i = -1
    return generateSequence {
        i++
        if (i +1> map { l -> l.count() }.multiply()) {
            null
        } else {
            numberToPermutation(i)
        }
    }
}

fun <T : Any, V : Iterable<T>> Iterable<V>.numberToPermutation(number: Int): List<T> {
    val reversed = this.reversed()
    val bases = reversed.map { it.count() }
    require(number >= 0 && number < bases.multiply()){number.toString()+"\n"+bases.multiply()}
    return reversed.mapIndexed { j, v -> v.toList()[getPositionInVariableBase(bases, number, j)] }.reversed()
}

private fun getPositionInVariableBase(bases: List<Int>, count: Int, position: Int): Int =
    bases.take(position).fold(count) { acc, item -> (acc - (acc % item)) / item } % bases[position]
