package wood.mike.integration

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification
import wood.mike.Actor
import wood.mike.Movie
import wood.mike.MovieService

import java.time.LocalDate
import java.time.Month

@Integration
@Rollback
class MovieServiceIntegrationSpec extends Specification {

    MovieService movieService

    def "test findAllMoviesForActor"() {
        given:
            def arnold = new Actor(forename: 'Arnold', surname: 'Schwarzenegger', dob: LocalDate.of( 1947, Month.JULY, 30 )).save()
            def predator = new Movie(title: 'Predator', releaseDate: LocalDate.of( 1988, Month.JANUARY, 1 ))
            predator.addToActors( arnold )
            predator.addToActors( new Actor( forename: 'Carl', surname: 'Weathers', dob: LocalDate.of( 1948, Month.JANUARY, 14 ) ) )
            predator.save()

            def terminator = new Movie(title: 'The Terminator', releaseDate: LocalDate.of( 1985, Month.JANUARY, 11 ))
            terminator.addToActors( arnold )
            terminator.save()
        when:
            def foundMovies = movieService.findAllMoviesForActor( arnold )
        then:
            foundMovies.size() == 2
    }
}
