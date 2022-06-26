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

    <g:render template="/movie/movieTable" model="[movieList: actor.movies]" />
</form>
</body>