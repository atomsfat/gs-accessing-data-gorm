@Grab("org.grails:gorm-hibernate4-spring-boot:1.0.0.M1")
@Grab("com.h2database:h2:1.3.173")

import grails.persistence.*
import org.springframework.http.*
import static org.springframework.web.bind.annotation.RequestMethod.*


@RestController
class GreetingController {

    @RequestMapping(value="/person/greet", method = GET)
    String greet(String firstName) {
        def p = Person.findByFirstName(firstName)
        return p ? "Hello ${p.firstName}!" : "Person not found"
    }

    @RequestMapping(value = '/person/add', method = POST)
    ResponseEntity addPerson(String firstName, String lastName) {
        Person.withTransaction {
            def p = new Person(firstName: firstName, lastName: lastName).save()
            if(p) {
                return new ResponseEntity(HttpStatus.CREATED)
            }
            else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST)
            }
        }
    }
}

@Entity
class Person {
    String firstName
}

