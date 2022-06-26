<table class="table">
    <caption>Actors</caption>
    <thead class="thead-light">
    <tr>
        <th scope="col">Name</th>
        <th scope="col">DOB</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${actorList}" var="anActor">
        <tr>
            <td><g:link controller="actor" action="show" id="${anActor.id}">${anActor.forename} ${anActor.surname}</g:link></td>
            <td>${anActor.dob}</td>
        </tr>
    </g:each>
    </tbody>
</table>