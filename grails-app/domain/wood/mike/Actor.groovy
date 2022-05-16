package wood.mike

import java.time.LocalDate

class Actor {

    String forename
    String surname
    LocalDate dob

    static hasMany = [movies: Movie]

    static constraints = {
    }
}
