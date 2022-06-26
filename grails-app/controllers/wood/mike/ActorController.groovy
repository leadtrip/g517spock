package wood.mike

class ActorController {

    def actorService

    def index() { }

    def show() {
        respond actorService.get( params.id )
    }
}
