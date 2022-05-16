<table>
    <tr>
        <th>Movie</th>
        <th>Release date</th>
    </tr>
        <g:each in="${moviesForActor}" var="mov">
            <tr>
                <td>${mov.title}</td>
                <td>${mov.releaseDate}</td>
            </tr>
        </g:each>
</table>