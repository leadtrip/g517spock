package wood.mike

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class MovieControllerSpecMock extends Specification implements ControllerUnitTest<MovieController>, DataTest{

    def movieService = Mock( MovieService )

    void setupSpec() {
        mockDomains Movie, Actor
    }

    def setup() {
        controller.movieService = movieService
    }

    void "test search"() {
        given:
            def arnie = new Actor(forename: 'Arnold', surname: 'Schwarzenegger', dob: LocalDate.of( 1947, Month.JULY, 30 ))
        when:
            params.actor = arnie
            controller.search()
        then:
            1 * movieService.findAllMoviesForActor(_)
    }

    void "test search verify arguments"() {
        given:
            def arnie = new Actor(forename: 'Arnold', surname: 'Schwarzenegger', dob: LocalDate.of( 1947, Month.JULY, 30 ))
        when:
            params.actor = arnie
            controller.search()
        then:
           1 * movieService.findAllMoviesForActor(_) >> {
               actor ->
               def actor0 = actor[0]
               assert actor0.forename == 'Arnold'
               assert actor0.surname == 'Schwarzenegger'
           }
    }
}
