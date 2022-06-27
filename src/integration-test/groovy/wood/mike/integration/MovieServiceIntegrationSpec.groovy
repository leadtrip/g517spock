package wood.mike.integration

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification
import wood.mike.Movie

import java.time.LocalDate

@Integration
@Rollback
class MovieServiceIntegrationSpec extends Specification{

    def movieService

    void "test getMovieWithNewSession"() {
        given:
            def movie = new Movie(title: 'The rainbow', releaseDate: LocalDate.now()).save(flush: true)
        when:
            def newSessionMovie = movieService.getMovieWithNewSession(movie.id)
        and:
            def standardMovie = movieService.get(movie.id)
        then:
            standardMovie
            newSessionMovie     // doesn't work, it's null
    }

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
            new Movie(title: title, releaseDate: LocalDate.now()).save(flush: true)
        }
    }

    void "test getMovieWithNewTransaction"() {
        given:
            def movie = new Movie(title: 'Bread bin', releaseDate: LocalDate.now()).save(flush: true)
        when:
            def newTransactionMovie = movieService.getMovieWithNewTransaction(movie.id)
        and:
            def standardMovie = movieService.get(movie.id)
        then:
            standardMovie
            newTransactionMovie     // doesn't work, it's null
    }
}
