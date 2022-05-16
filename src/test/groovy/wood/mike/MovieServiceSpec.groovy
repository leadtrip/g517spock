package wood.mike

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class MovieServiceSpec extends Specification implements ServiceUnitTest<MovieService>, DataTest{

    void setupSpec() {
        mockDomains Actor, Movie
    }

    void "test findActorsForMovie standard" () {
        given:
            def predator = new Movie(title: 'Predator', releaseDate: LocalDate.of( 1988, Month.JANUARY, 1 )).save()
            predator.addToActors( new Actor(forename: 'Arnold', surname: 'Schwarzenegger', dob: LocalDate.of( 1947, Month.JULY, 30 )) )
            predator.addToActors( new Actor( forename: 'Carl', surname: 'Weathers', dob: LocalDate.of( 1948, Month.JANUARY, 14 ) ) )
        when:
            def res = service.findActorsForMovie( predator )
        then:
           res == ['Arnold Schwarzenegger [1947-07-30]', 'Carl Weathers [1948-01-14]']
    }

    /**
     * Mock a method of the class under test without resorting to modifying the metaclass
     */
    void "test findActorsForMovie mocking method of class under test" () {
        given:
            def mockService =
                    [getActorAsString: { actor-> '[' + actor.dob + '] ' + actor.forename + ' ' + actor.surname  }] as MovieService
        and:
            def predator = new Movie(title: 'Predator', releaseDate: LocalDate.of( 1988, Month.JANUARY, 1 )).save()
            predator.addToActors( new Actor(forename: 'Arnold', surname: 'Schwarzenegger', dob: LocalDate.of( 1947, Month.JULY, 30 )) )
            predator.addToActors( new Actor( forename: 'Carl', surname: 'Weathers', dob: LocalDate.of( 1948, Month.JANUARY, 14 ) ) )
        when:
            def res = mockService.findActorsForMovie( predator )
        then:
            res == ['[1947-07-30] Arnold Schwarzenegger', '[1948-01-14] Carl Weathers']
    }
}
