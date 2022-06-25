package wood.mike

import java.time.LocalDate

class Movie {

    String title
    LocalDate releaseDate

    static hasMany = [actors: Actor]

    static constraints = {
    }
}
