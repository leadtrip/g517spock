package wood.mike

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class HelperMethods extends Specification implements DataTest{

    void setup() {
        mockDomains Actor, Movie
    }

    ActorService actorService = Spy()

    def "test one"() {
        given:
            def jackNicholson = new Actor(forename: 'Jack', surname: 'Nicholson', dob: LocalDate.of( 1937, Month.APRIL, 22 )).save(failOnError: true)

            def theShining = new Movie(title: 'The shining', releaseDate: LocalDate.of( 1980, Month.NOVEMBER, 7))
            theShining.addToActors(jackNicholson)
            theShining.save(failOnError: true)

            def oneFlewOverTheCuckoosNest = new Movie( title: "One Flew Over The Cuckoo's Nest", releaseDate: LocalDate.of( 1975, Month.NOVEMBER, 19 ) )
            oneFlewOverTheCuckoosNest.addToActors(jackNicholson)
            oneFlewOverTheCuckoosNest.save(failOnError: true)
        when:
            def foundMovies = actorService.moviesForActor( jackNicholson.id )
        then:
            validateMovies(foundMovies)
    }

    /**
     * Use assert statements otherwise you won't get the nice breakdown of exact error
     */
    void validateMovies( Set<Movie> foundMovies ) {
        assert foundMovies.size() == 2
        assert foundMovies.find{it.title == 'The shining'}
        assert foundMovies.find{it.title == "One Flew Over The Cuckoo's Nest"}
        assert foundMovies*.actors.unique().size() == 1
    }
}
