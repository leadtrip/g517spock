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
        def danny = new Actor( forename: 'Danny', surname: 'Devito', dob: LocalDate.of( 1944, Month.NOVEMBER, 17 ) ).save()
        def carl = new Actor( forename: 'Carl', surname: 'Weathers', dob: LocalDate.of( 1948, Month.JANUARY, 14 ) ).save()
        def kathyrn = new Actor( forename: 'Kathryn', surname: 'Harrold', dob: LocalDate.of( 1950, Month.AUGUST, 2 ) ).save()
        def linda = new Actor( forename: 'Linda', surname: 'Hamilton', dob: LocalDate.of( 1956, Month.SEPTEMBER, 26 ) ).save()
        def michael = new Actor( forename: 'Michael', surname: 'Biehn', dob: LocalDate.of( 1956, Month.JULY, 31 ) ).save()
        def sigourney = new Actor( forename: 'Sigourney', surname: 'Weaver', dob: LocalDate.of( 1949, Month.OCTOBER, 8 ) ).save()

        def rawDeal = new Movie( title: 'Raw deal', releaseDate: LocalDate.of( 1986, Month.JUNE, 6 ) )
        rawDeal.addToActors( arnold )
        rawDeal.addToActors( kathyrn )
        rawDeal.save()

        def predator = new Movie(title: 'Predator', releaseDate: LocalDate.of( 1988, Month.JANUARY, 1 )).save()
        predator.addToActors( arnold )
        predator.addToActors( carl )
        predator.save()

        def terminator = new Movie(title: 'The Terminator', releaseDate: LocalDate.of( 1985, Month.JANUARY, 11 )).save()
        terminator.addToActors( arnold )
        terminator.addToActors( linda )
        terminator.addToActors( michael )
        terminator.save()

        def twins = new Movie(title: 'Twins', releaseDate: LocalDate.of( 1988, Month.DECEMBER, 9 )).save()
        twins.addToActors( arnold )
        twins.addToActors( danny )
        twins.save()

        def aliens = new Movie( title: 'Aliens', releaseDate: LocalDate.of( 1986, Month.AUGUST, 29 ) ).save()
        aliens.addToActors( michael )
        aliens.addToActors( sigourney )
    }

    def destroy = {
    }
}
