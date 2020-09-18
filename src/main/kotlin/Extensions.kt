fun <T, U> Iterable<T>.tensor(that: Iterable<U>): List<Pair<T, U>> =
        this.flatMap { i -> that.map { j -> Pair(i, j) } }
