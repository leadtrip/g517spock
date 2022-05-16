package wood.mike

import grails.gorm.transactions.Transactional

@Transactional
class MovieService {

    def findAllMoviesForActor( Actor actor ) {
        Movie.findAllByActors( actor )
    }
}
