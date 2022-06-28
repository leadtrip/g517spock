package wood.mike

import grails.gorm.transactions.Transactional

@Transactional
class MovieService {

    def allMovies() {
        Movie.all
    }

    def get(id) {
        Movie.get(id)
    }

    def findAllMoviesForActor( Actor actor ) {
        Movie.findAllByActors( actor )
    }

    def findActorsForMovie( Movie movie ) {
        movie.actors.collect{
            getActorAsString( it )
        }
    }

    def getActorAsString( Actor actor ) {
        actor.forename + ' ' + actor.surname + ' [' + actor.dob + ']'
    }

    def movieSearch( String movieTitle ) {
        Movie.findAllByTitleIlike( '%' + movieTitle + '%' )
    }

    def createMovie(params) {
        new Movie( title: params.title, releaseDate: params.releaseDate ).save(failOnError: true)
    }

    def updateMovie(params) {
        def movie = Movie.get(params.id)
        movie.title = params.title
        movie.save(failOnError: true)
    }

    def getMovieWithNewSession(id) {
        Movie.withNewSession {
            Movie.get(id)
        }
    }

    def getMovieWithNewTransaction( id ) {
        Movie.withNewTransaction {
            Movie.get(id)
        }
    }

    def createMovieWithNewSession(movieAttrs) {
        Movie.withNewSession {
            new Movie(movieAttrs).save()
        }
    }

    def createMovieWithNewTransaction(movieAttrs) {
        Movie.withNewSession {
            new Movie(movieAttrs).save()
        }
    }
}
