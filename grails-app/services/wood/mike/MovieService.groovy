package wood.mike

import grails.gorm.transactions.Transactional

@Transactional
class MovieService {

    def findAllMoviesForActor( Actor actor ) {
        Movie.findAllByActors( [actor] as Set )
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
}
