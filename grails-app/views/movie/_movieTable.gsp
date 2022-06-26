<table class="table">
    <caption>Movies</caption>
    <thead class="thead-light">
    <tr>
        <th>Title</th>
        <th>Release date</th>
    </tr>
    <g:each in="${movieList}" var="aMovie">
        <tr>
            <td><g:link controller="movie" action="show" id="${aMovie.id}">${aMovie.title}</g:link></td>
            <td>${aMovie.releaseDate}</td>
        </tr>
    </g:each>
</table>