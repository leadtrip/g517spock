package wood.mike

class ActorController {

    def actorService

    def index() {
        respond actorService.all()
    }

    def show() {
        respond actorService.get( params.id )
    }
}
