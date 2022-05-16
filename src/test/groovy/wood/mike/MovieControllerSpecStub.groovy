package wood.mike

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class MovieControllerSpecStub extends Specification implements ControllerUnitTest<MovieController>, DataTest{

    def movieService = Stub( MovieService )

    void setupSpec() {
        mockDomains Movie, Actor
    }

    def setup() {
        controller.movieService = movieService
    }

    void "test search"() {
        given:
            def arnie = new Actor(forename: 'Arnold', surname: 'Schwarzenegger', dob: LocalDate.of( 1947, Month.JULY, 30 ))
            def predator = new Movie(title: 'Predator', releaseDate: LocalDate.of( 1988, Month.JANUARY, 1 )).save()
            def terminator = new Movie(title: 'The Terminator', releaseDate: LocalDate.of( 1985, Month.JANUARY, 11 )).save()
            def twins = new Movie(title: 'Twins', releaseDate: LocalDate.of( 1988, Month.DECEMBER, 9 )).save()
            arnie.addToMovies( predator: predator, terminator: terminator, twins: twins )
            arnie.save()
        and:
            params.actor = arnie
        when:
            controller.search()
        then:
            movieService.findAllMoviesForActor(arnie) >> [predator, twins]
            view == '/movie/actor'
            model.moviesForActor.size() == 2
    }

}
