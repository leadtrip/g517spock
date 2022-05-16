package wood.mike

import grails.gorm.transactions.Transactional

@Transactional
class MovieService {

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
}
