package hello

import junit.framework.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import services.PersonService
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by tomas on 8/11/14.
 */

@Ignore
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@WebAppConfiguration
public class GormIntegrationTest extends Specification{


  @Autowired
  PersonService personService

  void "testing spock works"(){
    given:
      println  personService
    expect:
      personService.findAll().size() == 0

  }

}
