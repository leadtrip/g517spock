<html>
<head>
    <title>Actor</title>
    <meta name="layout" content="mylayout">
</head>
<body>
    <h1>Actor</h1>
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
</body>