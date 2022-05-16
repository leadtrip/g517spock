package wood.mike

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

/**
 * A spy is always based on a real object.
 */
class MovieControllerSpecSpy extends Specification implements ControllerUnitTest<MovieController>, DataTest{

    def movieService = Spy( MovieService )

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
            1 * movieService.findAllMoviesForActor(arnie)
            view == '/movie/actor'
            model.moviesForActor.size() == 3
    }

    void "test search verify arguments and callRealMethod" () {
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
            1 * movieService.findAllMoviesForActor(_) >> { actor ->
                assert actor[0].forename == 'Arnold'
                assert actor[0].surname == 'Schwarzenegger'
                callRealMethod()                                    // after checking args, call the real method
            }
        and:
            view == '/movie/actor'
            model.moviesForActor.size() == 3
    }

    void "test search verify arguments and return something different" () {
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
            1 * movieService.findAllMoviesForActor(_) >> { actor ->
                assert actor[0].forename == 'Arnold'
                assert actor[0].surname == 'Schwarzenegger'
                [predator]                                      // return one movie
            }
        and:
            view == '/movie/actor'
            model.moviesForActor.size() == 1
    }
}
