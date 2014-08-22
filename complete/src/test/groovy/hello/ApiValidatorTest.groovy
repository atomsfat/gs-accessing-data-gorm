package hello

import iamedu.raml.RamlHandlerService
import iamedu.raml.validator.ApiValidator
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by tomas on 8/22/14.
 */

class ApiValidatorTest extends Specification {

  void "testing spock works"() {
    given:
     RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
     ApiValidator av = ramlHandlerService.buildValidator()
     println av.handleResource("/sample-api/api/songs?query=help")
    expect:
      av != null




  }

}
