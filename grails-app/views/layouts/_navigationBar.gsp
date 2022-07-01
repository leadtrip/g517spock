<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Movies & Actors</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="movieDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Movie
                </a>
                <div class="dropdown-menu" aria-labelledby="movieDropdown">
                    <g:link controller="movie" class="dropdown-item">All</g:link>
                    <g:link controller="movie" action="random" class="dropdown-item">Random</g:link>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="actorDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Actor
                </a>
                <div class="dropdown-menu" aria-labelledby="actorDropdown">
                    <g:link controller="actor" class="dropdown-item">All</g:link>
                </div>
            </li>
        </ul>
    </div>
</nav>