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

    /**
     * A static closure on the instance does not get invoked when calling the non-static method
     */
    def "test static metaclass change to instance" () {
        given:
            def movieTitle = 'finding nemo'
            def silentHill = new Movie(title: movieTitle, releaseDate: LocalDate.of( 2003, Month.OCTOBER, 10 )).save()
            params.movieTitle = movieTitle
        and:
            def newMovieTitle = 'monsters inc'
            silentHill.metaClass.static.getTitle = { -> newMovieTitle }
        when:
            controller.movieSearch()
        then:
           model.movies[0].title == movieTitle      // same movie/title set at start
    }

    def "test domain save standard" () {
        given:
            params.title = 'Lucky'
            params.releaseDate = LocalDate.now()
        when:
            controller.create()
        then:
            Movie.findByTitle( params.title )
    }

    @ConfineMetaClassChanges(Movie)
    def "test domain save change domain metaclass save" () {
        given:
            params.title = 'Lucky'
            params.releaseDate = LocalDate.now()
        and:
            def saved = false
            Movie.metaClass.static.save = { Map args -> saved = true }      // this only works if static
        when:
            controller.create()
        then:
            saved
            !Movie.findByTitle( params.title )
    }

    /**
     * This would typically fail without ConfineMetaClassChanges on previous method
     */
    def "test domain save standard again 2" () {
        given:
            params.title = 'Cheese'
            params.releaseDate = LocalDate.now()
        when:
            controller.create()
        then:
            Movie.findByTitle( params.title )
    }

    def "test update standard" () {
        given:
            def americanPie = new Movie(title: 'american pie', releaseDate: LocalDate.of( 1999, Month.OCTOBER, 8 )).save()
            def newTitle = 'aliens'
        and:
            params.id = americanPie.id
            params.title = newTitle
        when:
            controller.update()
        then:
            Movie.get(americanPie.id).title == newTitle
    }

    def "test update change metaclass on instance" () {
        given:
            def tron = new Movie(title: 'tron', releaseDate: LocalDate.of( 1982, Month.OCTOBER, 21 )).save()
            def newTitle = 'jaws'
        and:
            params.id = tron.id
            params.title = newTitle
        and:
            def saved = false
            tron.metaClass.static.save = { Map args -> saved = true }       // seem to have to use static for save, even on instance
        and:
            assert Movie.get(tron.id).title == 'tron'
        when:
            controller.update()
        then:
            saved
            Movie.get(tron.id).title == newTitle        // the title is updated, which is odd?
    }

    def "test update change metaclass on instance with exception" () {
        given:
            def shrek = new Movie(title: 'shrek', releaseDate: LocalDate.of(2001, Month.JUNE, 29)).save()
            def newTitle = 'get out'
        and:
            params.id = shrek.id
            params.title = newTitle
        and:
            shrek.metaClass.static.save = { Map args -> throw new UnsupportedOperationException('no saving allowed') }
        and:
            assert Movie.get(shrek.id).title == 'shrek'
        when:
           controller.update()
        then:
            thrown(UnsupportedOperationException)
        and:
            !Movie.all      // unsure as to why there are no movies after this
    }
}
