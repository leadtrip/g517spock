package wood.mike

import grails.gorm.transactions.Transactional

@Transactional
class ActorService {

    def get( id ) {
        Actor.get( id )
    }

    def moviesForActor( Serializable id ) {
        Actor.get( id ).movies
    }
}
