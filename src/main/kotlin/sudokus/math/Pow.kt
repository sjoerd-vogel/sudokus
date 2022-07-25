package sudokus.math

fun Int.pow(power: Int): Int {
    require(power > 0)
    tailrec fun loop(remainder: Int, accum: Int = 1): Int {
        return if (remainder < 1) accum
        else loop(remainder - 1, accum * this)
    }
    return loop(power)
}
