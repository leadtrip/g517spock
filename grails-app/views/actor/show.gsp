<html>
<head>
    <title>Actor</title>
    <meta name="layout" content="mylayout">
</head>
<body>
<h1>Actor</h1>
<form>
    <div class="form-group">
        <label for="title" class="col-sm-2 col-form-label">Name</label>
        <input type="text" readonly class="form-control-plaintext" id="title" value="${actor.forename} ${actor.surname}">
    </div>
    <div class="form-group">
        <label for="releaseDate" class="col-sm-2 col-form-label">DOB</label>
        <input type="text" readonly class="form-control-plaintext" id="releaseDate" value="${actor.dob}">
    </div>

    <table class="table table-bordered table-dark table-hover">
        <caption>Movies</caption>
        <tr>
            <th>Title</th>
            <th>Release date</th>
        </tr>
        <g:each in="${actor.movies}" var="aMovie">
            <tr>
                <td><g:link controller="movie" action="show" id="${aMovie.id}">${aMovie.title}</g:link></td>
                <td>${aMovie.releaseDate}</td>
            </tr>
        </g:each>
    </table>
</form>
</body>