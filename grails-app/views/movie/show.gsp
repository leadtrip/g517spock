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

    <g:render template="/actor/actorTable" model="[actorList: movie.actors]" />
</body>
</html>