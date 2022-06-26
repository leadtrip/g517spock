package g517spock

import grails.gorm.transactions.Transactional
import wood.mike.Actor
import wood.mike.Movie

import java.time.LocalDate
import java.time.Month

class BootStrap {

    def init = { servletContext ->
        createMovies()
    }

    @Transactional
    def createMovies() {
        def arnold = new Actor(forename: 'Arnold', surname: 'Schwarzenegger', dob: LocalDate.of( 1947, Month.JULY, 30 )).save()

        def rawDeal = new Movie( title: 'Raw deal', releaseDate: LocalDate.of( 1986, Month.JUNE, 6 ) )
        rawDeal.addToActors( arnold )
        rawDeal.save()

        def predator = new Movie(title: 'Predator', releaseDate: LocalDate.of( 1988, Month.JANUARY, 1 )).save()
        predator.addToActors( arnold )
        predator.save()

        def terminator = new Movie(title: 'The Terminator', releaseDate: LocalDate.of( 1985, Month.JANUARY, 11 )).save()
        terminator.addToActors( arnold )
        terminator.save()

        def twins = new Movie(title: 'Twins', releaseDate: LocalDate.of( 1988, Month.DECEMBER, 9 )).save()
        twins.addToActors( arnold )
        twins.save()
    }

    def destroy = {
    }
}
