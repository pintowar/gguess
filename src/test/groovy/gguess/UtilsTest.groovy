package gguess

class UtilsTest extends spock.lang.Specification {

    def "RemoveAccents"() {
        expect:
        Utils.removeAccents(a) == b

        where:
        a                           | b
        'áâàäãéêèëẽíîìïĩóôòöõúûùüũ' | 'aaaaaeeeeeiiiiiooooouuuuu'
        'maarçíléa'                 | 'maarcilea'
        'thaíanny'                  | 'thaianny'
    }

    def "RemoveDuplicatedLetters"() {
        expect:
        Utils.removeDuplicatedLetters(a) == b

        where:
        a                           | b
        'aaaaaeeeeeiiiiiooooouuuuu' | 'aeiou'
        'maarçíléa'                 | 'marçíléa'
        'thaíanny'                  | 'thaíany'
    }

    def "Linspace"() {
        expect:
        Utils.linspace(a, b, c) == d as double[]

        where:
        a | b  | c || d
        5 | 10 | 2 || [0, 5]
        0 | 20 | 5 || [0, 5, 10, 15, 20]
        0 | 20 | 6 || [0, 4, 8, 12, 16, 20]
    }

    def "NormName"() {
        expect:
        Utils.normName(a) == b

        where:
        a                           | b
        'áâàäãéêèëẽíîìïĩóôòöõúûùüũ' | 'aeiou'
        'MaaRçíléa'                 | 'marcilea'
        'THaíanny'                  | 'thaiany'
        null                        | ''
    }

//
//    def "TransformString"() {
//    }
}
