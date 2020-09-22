package classic

import board.Coord
import classic.populate.areSectorsHorizontalCompatible
import classic.populate.areSectorsVerticalCompatible
import classic.populate.populateBoard

fun main() {
    println(populateBoard(1..9))
    val verGood = createClassicBoard<String>()
        .set(Coord(1, 1), "1")
        .set(Coord(1, 2), "2")
        .set(Coord(1, 3), "3")
        .set(Coord(2, 1), "4")
        .set(Coord(2, 2), "5")
        .set(Coord(2, 3), "6")

        .set(Coord(1, 4), "4")
        .set(Coord(1, 5), "5")
        .set(Coord(1, 6), "6")
        .set(Coord(2, 4), "1")
        .set(Coord(2, 5), "7")
    val verBad = verGood
        .set(Coord(2, 6), "8")
    println(
        verBad
    )
    println(areSectorsHorizontalCompatible(verBad,
        verBad.sectors.first { Coord(1, 1) in it . coords },
        verBad.sectors.first { Coord(1, 4) in it . coords })
    )
    println(areSectorsVerticalCompatible(verBad,
        verBad.sectors.first { Coord(1, 1) in it . coords },
        verBad.sectors.first { Coord(1, 4) in it . coords })
    )
    println(areSectorsHorizontalCompatible(verGood,
        verBad.sectors.first { Coord(1, 1) in it . coords },
        verBad.sectors.first { Coord(1, 4) in it . coords })
    )
    println(areSectorsVerticalCompatible(verGood,
        verBad.sectors.first { Coord(1, 1) in it . coords },
        verBad.sectors.first { Coord(1, 4) in it . coords })
    )
}