package sudokus

//calculate the IntRange count without actually counting
//faster for large ranges, but still presumes the value is below Int.MAX_VALUE
//it's a bit silly this is not the default
fun IntProgression.fastCount(): Int = when {
    isEmpty() -> null
    first >= 0 -> last - first
    last >= 0 -> -first - -last
    else -> -first - -last
}?.div(step)?.inc() ?: 0


