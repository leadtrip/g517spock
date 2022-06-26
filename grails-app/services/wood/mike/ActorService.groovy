package wood.mike

import grails.gorm.transactions.Transactional

@Transactional
class ActorService {

    def get( id ) {
        Actor.get( id )
    }

    def all() {
        Actor.all
    }

    def moviesForActor( Serializable id ) {
        Actor.get( id ).movies
    }
}
