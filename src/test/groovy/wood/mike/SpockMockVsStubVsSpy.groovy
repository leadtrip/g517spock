package wood.mike

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class SpockMockVsStubVsSpy extends Specification implements ControllerUnitTest<MovieController>, DataTest{

    void setupSpec() {
        mockDomains Movie
    }

    /**
     * Mocking is the act of describing (mandatory) interactions between the object under specification and its collaborators
     */
    def "test mock service" () {
        given:
            def mockMovieService = Mock( MovieService )
            controller.movieService = mockMovieService
        and:
            params.movieTitle = 'moon'
        when:
            controller.movieSearch()
        then:
            1 * mockMovieService.movieSearch(_)     // must specify the cardinality with a mock but return not required
        and:
            !model.movies
        when:
            controller.movieSearch()
        then:
            1 * mockMovieService.movieSearch(_) >> 'godzilla'   // this time we are returning something
        and:
            model.movies == 'godzilla'
    }

    /**
     * Whereas a mock can be used both for stubbing and mocking, a stub can only be used for stubbing.
     * Limiting a collaborator to a stub communicates its role to the readers of the specification.
     */
    def "test stub service" () {
        given:
            def stubMovieService = Stub( MovieService )
            controller.movieService = stubMovieService
        and:
            params.movieTitle = 'moon'
        when:
            controller.movieSearch()
        then:
           stubMovieService.movieSearch(_) >> 'anything' // no need for cardinality with stub but must return something if method does
        and:
            model.movies == 'anything'
    }

    /**
     * A spy is always based on a real object. Hence you must provide a class type rather than an interface type,
     * along with any constructor arguments for the type. If no constructor arguments are provided, the type’s no-arg constructor will be used.
     */
    def "test spy service" () {
        given:
            def spyMovieService = Spy( MovieService )
            controller.movieService = spyMovieService
        and:
            params.movieTitle = 'moon'
            new Movie( title: params.movieTitle, releaseDate: LocalDate.of( 2009, Month.JULY, 17 ) ).save()
        when:
            controller.movieSearch()
        then:
            1 * spyMovieService.movieSearch(params.movieTitle) // we're using the real implementation with a spy, we could just leave this off so long as some side effect indicates it's run which it has below
            model.movies[0].title == params.movieTitle
    }
}
