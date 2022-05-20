package wood.mike

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

import java.time.LocalDate
import java.time.Month

/**
 * Testing various combinations of modifying the metaclass and the side effects using @ConfineMetaClassChanges
 * Try removing the @ConfineMetaClassChanges annotation from the methods below to see different outcomes
 */
class ConfineMetaClassChangesSpec extends Specification implements ControllerUnitTest<MovieController>, DataTest{

    def movieService = Spy(MovieService)

    void setupSpec() {
        mockDomains Movie
    }

    void setup() {
        controller.movieService = movieService
    }

    def "test standard"() {
        given:
            def movieTitle = 'rambo'
            new Movie(title: movieTitle, releaseDate: LocalDate.of( 1982, Month.DECEMBER, 16 )).save()
            params.movieTitle = movieTitle
        when:
            controller.movieSearch()
        then:
            model.movies[0].title == movieTitle
    }

    @ConfineMetaClassChanges(Movie)
    def "test change dynamic finder"() {
        given:
            def movieTitle = 'rambo'
            new Movie(title: movieTitle, releaseDate: LocalDate.of( 1982, Month.DECEMBER, 16 )).save()
            params.movieTitle = movieTitle

            def goneWithTheWind = new Movie(title: 'gone with the wind', releaseDate: LocalDate.of( 1940, Month.APRIL, 18 ))
        and:
            Movie.metaClass.static.findAllByTitleIlike = { String title -> [goneWithTheWind] }
        when:
            controller.movieSearch()
        then:
           model.movies[0].title == goneWithTheWind.title
    }

    /**
     * Without @ConfineMetaClassChanges on the feature method above this typically fails because the dynamic finder
     * findAllByTitleIlike on Movie has been permanently changed for the duration of this test class and maybe beyond
     */
    def "test standard again"() {
        given:
            def movieTitle = 'the omen'
            new Movie(title: movieTitle, releaseDate: LocalDate.of( 1976, Month.JUNE, 6 )).save()
            params.movieTitle = movieTitle
        when:
            controller.movieSearch()
        then:
           model.movies[0].title == movieTitle
    }

    /**
     * No need to use @ConfineMetaClassChanges when using global mocks because...
     *
     * Usually, Groovy mocks need to be injected into the code under specification just like regular mocks.
     * However, when a Groovy mock is created as global, it automagically replaces all real instances of the mocked type
     * for the duration of the feature method.
     */
    def "test global GroovyMock"() {
        given:
            def movieTitle = 'the omen'
            new Movie(title: movieTitle, releaseDate: LocalDate.of( 1976, Month.JUNE, 6 )).save()
            params.movieTitle = movieTitle
        and:
            def thePoltergeist = new Movie( title: 'the poltergeist', releaseDate: LocalDate.of( 1982, Month.SEPTEMBER, 16 ) )
        and:
            GroovyMock(Movie, global: true)
            Movie.findAllByTitleIlike(_) >> [thePoltergeist]
        when:
            controller.movieSearch()
        then:
           model.movies[0].title == thePoltergeist.title
    }

    def "test standard again 2"() {
        given:
            def movieTitle = 'rain man'
            new Movie(title: movieTitle, releaseDate: LocalDate.of( 1989, Month.MARCH, 3 )).save()
            params.movieTitle = movieTitle
        when:
            controller.movieSearch()
        then:
            model.movies[0].title == movieTitle
    }

    /**
     * This requires @ConfineMetaClassChanges because we're modifying the class
     */
    @ConfineMetaClassChanges(Movie)
    def "test non static metaclass change to class" () {
        given:
            def movieTitle = 'sunshine'
            new Movie(title: movieTitle, releaseDate: LocalDate.of( 2007, Month.APRIL, 5 )).save()
            params.movieTitle = movieTitle
        and:
            def newMovieTitle = 'mary poppins'
            Movie.metaClass.getTitle = { -> newMovieTitle }
        when:
            controller.movieSearch()
        then:
            model.movies[0].title == newMovieTitle
    }

    def "test standard again 3"() {
        given:
            def movieTitle = 'point break'
            new Movie(title: movieTitle, releaseDate: LocalDate.of( 1991, Month.JULY, 12 )).save()
            params.movieTitle = movieTitle
        when:
            controller.movieSearch()
        then:
            model.movies[0].title == movieTitle
    }

    /**
     * This does not need @ConfineMetaClassChanges because we're modifying an instance
     */
    def "test non static metaclass change to instance" () {
        given:
            def movieTitle = 'silent hill'
            def silentHill = new Movie(title: movieTitle, releaseDate: LocalDate.of( 2006, Month.APRIL, 21 )).save()
            params.movieTitle = movieTitle
        and:
            def newMovieTitle = 'gladiator'
            silentHill.metaClass.getTitle = { -> newMovieTitle }
        when:
            controller.movieSearch()
        then:
            model.movies[0].title == newMovieTitle
    }

    def "test standard again 4"() {
        given:
            def movieTitle = 'the matrix'
            new Movie(title: movieTitle, releaseDate: LocalDate.of( 1999, Month.JUNE, 11 )).save()
            params.movieTitle = movieTitle
        when:
            controller.movieSearch()
        then:
           model.movies[0].title == movieTitle
    }
}
