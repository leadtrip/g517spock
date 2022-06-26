<html>
<head>
    <title>Movies</title>
    <meta name="layout" content="mylayout">
</head>

<body>
    <h1>Movies</h1>
    <table>
        <tr>
            <th>Title</th>
            <th>Release date</th>
        </tr>
        <g:each in="${allMovies}" var="aMovie">
            <tr>
                <td><g:link action="show" id="${aMovie.id}">${aMovie.title}</g:link></td>
                <td>${aMovie.releaseDate}</td>
            </tr>
        </g:each>
    </table>
</body>
</html>