package wood.mike

class MovieController {

    def movieService

    def index() {
        [movieList: movieService.allMovies()]
    }

    def show() {
        respond movieService.get(params.id)
    }

    def search() {
        if ( params.actor ) {
            def moviesForActor = movieService.findAllMoviesForActor( params.actor )
            render( view: 'actor', model: [moviesForActor: moviesForActor] )
            return
        }
        render( view: 'search' )
    }

    def movieSearch() {
        if ( params.movieTitle ) {
            def movies = movieService.movieSearch( params.movieTitle )
            render( view: movies, model: [movies: movies] )
            return
        }
        render (view: 'search')
    }

    def create() {
        movieService.createMovie(params)
    }

    def update() {
        movieService.updateMovie(params)
    }

    def random() {

    }

    def updateThenFlushInWithNewSession() {
        def movie = Movie.findByTitle('Raw deal')
        render (view: 'random', model: [result: movieService.updateThenFlushInWithNewSession(movie.id)] )
    }
}
