package hello

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.*
import services.PersonService

import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
class GreetingController {

   @Autowired
    PersonService personService

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class)

    @RequestMapping(value="/person/greet", method = GET)
    String greet(String firstName) {
        println personService
        logger.info("weeee --->>>pe" )
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

