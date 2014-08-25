package iamedu.raml

import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by tomas on 8/25/14.
 */
class RamlApiHandlerTest extends Specification {

  HttpServletRequest request
  HttpServletResponse response
  ApplicationContext appContext

  void setup() {
    appContext = Mock(ApplicationContext)
    request = Mock(HttpServletRequest)
    response = Mock(HttpServletResponse)
    request.getMethod() >> { "get" }
    request.getHeaderNames() >> {
      return new Vector<String>().elements()
    }
    request.getHeaders("accept") >> {
      Vector<String> h = new Vector<String>()
      h.add('application/json')
      h.elements()
    }
    request.getQueryString() >> { "query=metro" }
    request.getParameterMap() >> {
      Map<String, String[]> params = new HashMap<String, String[]>()
      params.put("query", ["metro"])
      return params
    }
    request.getRequestURI() >> {
      "/sample-api/api/songs"
    }

    appContext.containsBean(_) >> {false}

  }

  def "Handle"() {
    when:
    RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
    RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, false)
    def res = handler.handle(request, response)
    "RamlApiHandlerTest.handle $res"
    then:
    res != null

  }
}
