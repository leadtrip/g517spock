package wood.mike.func

import geb.spock.GebSpec
import grails.testing.mixin.integration.Integration

@Integration
class MovieControllerFunctionalSpec extends GebSpec{

    void "test index"() {
        when:
            go '/movie'
        then:
            title == 'Movies'
    }
}
