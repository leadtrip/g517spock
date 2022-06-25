package wood.mike.integration

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Shared
import spock.lang.Specification
import wood.mike.Actor
import wood.mike.ActorService
import wood.mike.Movie

import java.time.LocalDate
import java.time.Month

@Integration
@Rollback
class ActorServiceIntegrationSpec extends Specification {

    ActorService actorService

    @Shared
    Actor arnold

    @Shared
    Movie rawDeal

    /**
     * Data added to DB in setup is not rolled back like data added in test
     */
    void setup() {
        arnold = new Actor(forename: 'Arnold', surname: 'Schwarzenegger', dob: LocalDate.of( 1947, Month.JULY, 30 )).save()
        rawDeal = new Movie( title: 'Raw deal', releaseDate: LocalDate.of( 1986, Month.JUNE, 6 ) )
        rawDeal.addToActors( arnold )
        rawDeal.save()
    }

    /**
     * We need this because we added data to the DB in setup which isn't rolled back
     */
    void cleanup() {
        Actor.executeUpdate('delete from Actor')
        Movie.executeUpdate('delete from Movie')
    }

    def "test data added in setup"() {
        expect:
            actorService.moviesForActor(arnold.id).size() == 1
    }

    /**
     * All data added in here is rolled back
     */
    def "test findAllMoviesForActor"() {
        given:
            def carl = new Actor( forename: 'Carl', surname: 'Weathers', dob: LocalDate.of( 1948, Month.JANUARY, 14 ) ).save()
        and:
            def predator = new Movie(title: 'Predator', releaseDate: LocalDate.of( 1988, Month.JANUARY, 1 ))
            predator.addToActors( arnold )
            predator.addToActors( carl )
            predator.save()

            def terminator = new Movie(title: 'The Terminator', releaseDate: LocalDate.of( 1985, Month.JANUARY, 11 ))
            terminator.addToActors( arnold )
            terminator.save()
        when:
            def foundMovies = actorService.moviesForActor(arnold.id)
        then:
            Actor.list().size() == 2
        and:
            Movie.list().size() == 3
        and:
            Movie.list().each {println it.title + ' ' + it.actors*.forename}
        and:
            foundMovies.size() == 3
            foundMovies.find{ it.title == rawDeal.title }
            foundMovies.find{it.title == predator.title}
            foundMovies.find{it.title == terminator.title}
    }
}
