package hello

import iamedu.raml.RamlHandlerService
import iamedu.raml.validator.ApiValidator
import iamedu.raml.validator.EndpointValidator
import spock.lang.Ignore
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * Created by tomas on 8/22/14.
 */

class ApiValidatorTest extends Specification {

  void "testing spock works"() {

    setup:
      HttpServletRequest request = Mock(HttpServletRequest)

      request.getMethod() >> {"get"}
      request.getHeaderNames() >> {
        return  new Vector<String>().elements()
      }
      request.getHeaders("accept") >> {
        Vector<String> h = new Vector<String>()
        h.add('Accept-Language')
        h.elements()
      }
    request.getQueryString() >> {"query=metro"}
    request.getParameterMap() >> {
      Map<String, String[] > params = new HashMap<String, String[]>()
      params.put("query", ["metro"])
      return params
    }

    when:
     println request
     println request.getMethod()
     println request.getQueryString()
     println request.getParameterMap()
     RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
     ApiValidator av = ramlHandlerService.buildValidator()
     def (EndpointValidator validator, params) = av.handleResource("/sample-api/api/songs")
     def result = validator.handleRequest(request)
     validator.handleResponse(false, request, null, false)
     println result
    then:

      validator != null
      av != null




  }

}
