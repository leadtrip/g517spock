package wood.mike

import spock.lang.Specification

class ErrorHandling extends Specification{

    void "test one"() {
        when:
            "I'm a string".nonExistentMethod()
        then:
            thrown(MissingMethodException)
    }

    void "test two"() {
        given:
            def str = "I'm a string"
            str.metaClass.nonExistentMethod = { -> 'and so am I' }
        when:
            str.nonExistentMethod()
        then:
            notThrown(MissingMethodException)
    }

    void "test three"() {
        when:
            [].get(0)
        then:
            def e = thrown(IndexOutOfBoundsException)
            e.message == 'Index: 0, Size: 0'
    }
}
