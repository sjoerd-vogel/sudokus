package com.vogel.sjoerd.sudokus

object Extensions {
    fun <T, U> Iterable<T>.tensor(that: Iterable<U>): Iterable<Pair<T, U>> = flatMap { thisItem ->
        that.map { thatItem -> Pair(thisItem, thatItem) }
    }

    fun <T> Iterable<T>.selfTensor(): Iterable<Pair<T, T>> = tensor(this)
}