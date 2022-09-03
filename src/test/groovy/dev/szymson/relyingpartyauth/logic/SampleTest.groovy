package dev.szymson.relyingpartyauth.logic

import spock.lang.Specification

class SampleTest extends Specification {

    def 'sample test should be possitive' () {
        given:
        def a = 1
        def b = a+1

        when:
        def c = b-1

        then:
        a == c
    }
}
