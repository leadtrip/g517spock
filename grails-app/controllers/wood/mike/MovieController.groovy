package wood.mike

class MovieController {

    def movieService

    def index() { }

    def search() {
        if ( params.actor ) {
            def moviesForActor = movieService.findAllMoviesForActor( params.actor )
            render( view: 'actor', model: [moviesForActor: moviesForActor] )
            return
        }
        render( view: 'search' )
    }
}
