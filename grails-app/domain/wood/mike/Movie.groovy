package wood.mike

import java.time.LocalDate

class Movie {

    String title
    LocalDate releaseDate

    static belongsTo = Actor
    static hasMany = [actors: Actor]

    static constraints = {
    }
}
