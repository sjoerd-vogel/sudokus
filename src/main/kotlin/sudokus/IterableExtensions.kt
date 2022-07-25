package sudokus

fun <T, U> Iterable<T>.tensor(that: Iterable<U>): Iterable<Pair<T, U>> = flatMap { thisItem ->
    that.map { thatItem -> Pair(thisItem, thatItem) }
}

fun <T> Iterable<T>.selfTensor(): Iterable<Pair<T, T>> = tensor(this)

fun Iterable<Int>.multiply(): Int = fold(1) { i, j -> i * j }

fun <T> List<T>.getPermutationsWithoutRepeats(): Sequence<List<T>> {
    val bases = (2 until this.size)
        .fold(listOf(1)) { l, i -> l + l.last() * i }
        .reversed() + 0
    val iterator = (0 until bases.first() * this.size).iterator()
    return getPermutationsWithoutRepeatsFromBasesAndIterator(bases, iterator)
}

private fun <T> List<T>.getPermutationsWithoutRepeatsFromBasesAndIterator(
    bases: List<Int>,
    iterator: Iterator<Int>
): Sequence<List<T>> =
    generateSequence { if (iterator.hasNext()) this.getPermutation(iterator.next(), bases) else null }

fun <T> List<T>.getPermutation(dec: Int, bases: List<Int>): List<T> {
    tailrec fun loop(decRemainder: Int = dec, listRemainder: List<T> = this, accum: List<T> = listOf()): List<T> {
        if (listRemainder.isEmpty()) return accum
        val base = bases[this.size - listRemainder.size]
        val newDec = if (base > 0) decRemainder % base else 0
        val index = if (listRemainder.size > 1) decRemainder / base else 0
        return loop(
            newDec,
            listRemainder.filterIndexed { i, _ -> i != index },
            accum + listRemainder[index]
        )
    }
    return loop()
}

//doesn't get rid of repeats, TODO come up with an algorithm that doesn't have repeats
fun <T : Any> Iterable<T>.selfCombinations(): Sequence<List<T>> =
    List(this.count()) { this }
        .combinations()

fun <T : Any, V : Iterable<T>> Iterable<V>.combinations(): Sequence<List<T>> =
    combinations((0 until map { it.count() }.multiply()).iterator())

fun Int.pow(power: Int): Int {
    require(power > 0)
    tailrec fun loop(remainder: Int, accum: Int = 1): Int {
        return if (remainder < 1) accum
        else loop(remainder - 1, accum * this)
    }
    return loop(power)
}

private fun <T : Any, V : Iterable<T>> Iterable<V>.combinations(iterator: Iterator<Int>): Sequence<List<T>> {
    return generateSequence {
        if (iterator.hasNext()) {
            numberToCombination(iterator.next())
        } else {
            null
        }
    }
}

//generalization of the factoradic trick of turning numbers into permutations
fun <T : Any, V : Iterable<T>> Iterable<V>.numberToCombination(number: Int): List<T> {
    val reversed = this.reversed()
    val bases = reversed.map { it.count() }
    require(number >= 0 && number < bases.multiply())
    return reversed.mapIndexed { j, v -> v.toList()[getPositionInVariableBase(bases, number, j)] }.reversed()
}

private fun getPositionInVariableBase(bases: List<Int>, count: Int, position: Int): Int =
    bases.take(position).fold(count) { acc, item -> (acc - (acc % item)) / item } % bases[position]
