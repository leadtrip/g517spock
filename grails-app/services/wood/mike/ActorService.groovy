package wood.mike

import grails.gorm.transactions.Transactional

@Transactional
class ActorService {

    def moviesForActor( Serializable id ) {
        Actor.get( id ).movies
    }
}
