<html>
<head>
    <title>Movie</title>
    <meta name="layout" content="mylayout">
</head>

<body>
    <h1>${movie.title}</h1>
    <form>
        <div class="form-group row">
            <label for="title" class="col-sm-2 col-form-label">Title</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" id="title" value="${movie.title}">
            </div>
        </div>
        <div class="form-group row">
            <label for="releaseDate" class="col-sm-2 col-form-label">Release date</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" id="releaseDate" value="${movie.releaseDate}">
            </div>
        </div>
    </form>

    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">DOB</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${movie.actors}" var="anActor">
            <tr>
                <td><g:link controller="actor" action="show" id="${anActor.id}">${anActor.forename} ${anActor.surname}</g:link></td>
                <td>${anActor.dob}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</body>
</html>