package wood.mike.integration

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification
import wood.mike.Movie

import java.time.LocalDate
import java.time.Month

@Integration
@Rollback
class MovieServiceIntegrationSpec extends Specification{

    def movieService

    /**
     * Method getMovieWithNewSession in movieService uses withNewSession to fetch the movie which doesn't find the movie
     * we just added in the test
     */
    void "test getMovieWithNewSession"() {
        given:
            def movie = new Movie(title: 'The rainbow', releaseDate: LocalDate.now()).save(flush: true)
        when:
            def newSessionMovie = movieService.getMovieWithNewSession(movie.id)
        and:
            def standardMovie = movieService.get(movie.id)
        then:
            standardMovie
            !newSessionMovie     // not found by service
    }

    /**
     * As above, calling movieService getMovieWithNewSession but this time we added the movie in a new transaction in
     * the test setup and this time the movie is found in the service, assuming as it's been committed?
     */
    void "test getMovieWithNewSession create movie in separate transaction"() {
        given:
            def title = 'The cod father'
            createMovieInNewTransaction( title)
            def movie = Movie.findByTitle( title )
        when:
            def newSessionMovie = movieService.getMovieWithNewSession(movie.id)
        and:
            def standardMovie = movieService.get(movie.id)
        then:
            standardMovie
            newSessionMovie     // this works because we added the movie in a new transaction so it's committed
    }

    def createMovieInNewTransaction(title ) {
        Movie.withNewTransaction {
            new Movie(title: title, releaseDate: LocalDate.now()).save()
        }
    }

    /**
     * Calling method on service that finds movie using withNewTransaction, as per withNewSession the movie we added in
     * this test setup isn't found
     */
    void "test getMovieWithNewTransaction"() {
        given:
            def movie = new Movie(title: 'Bread bin', releaseDate: LocalDate.now()).save(flush: true)
        when:
            def newTransactionMovie = movieService.getMovieWithNewTransaction(movie.id)
        and:
            def standardMovie = movieService.get(movie.id)
        then:
            standardMovie
            !newTransactionMovie     // not found by service
    }

    /**
     * As above, calling movieService getMovieWithNewTransaction but this time we added the movie in a new transaction in
     * the test setup and this time the movie is found in the service, assuming as it's been committed?
     */
    void "test getMovieWithNewTransaction create movie in separate transaction"() {
        given:
            def title = 'Abattoir'
            createMovieInNewTransaction( title)
            def movie = Movie.findByTitle( title )
        when:
            def newTransactionMovie = movieService.getMovieWithNewTransaction(movie.id)
        and:
            def standardMovie = movieService.get(movie.id)
        then:
            standardMovie
            newTransactionMovie
    }

    /**
     * Creating a movie in a new session in the service doesn't cause any issues
     */
    void "test createMovieWithNewSession"() {
        given:
            def title = 'The ring'
            def releaseDate = LocalDate.of(2003, Month.FEBRUARY, 21)
            def movieAttrs = [title: title, releaseDate: releaseDate]
        when:
            def savedMovie = movieService.createMovieWithNewSession(movieAttrs)
        then:
            savedMovie.title == title
            savedMovie.releaseDate == releaseDate
        and:
            Movie.findByTitle(title)
    }

    /**
     * Creating a movie in a new transaction in the service doesn't cause any issues
     */
    void "test createMovieWithNewTransaction"() {
        given:
            def title = 'The lawnmower man'
            def releaseDate = LocalDate.of(1992, Month.MARCH, 6)
            def movieAttrs = [title: title, releaseDate: releaseDate]
        when:
            def savedMovie = movieService.createMovieWithNewTransaction(movieAttrs)
        then:
            savedMovie.title == title
            savedMovie.releaseDate == releaseDate
        and:
            Movie.findByTitle(title)
    }
}
