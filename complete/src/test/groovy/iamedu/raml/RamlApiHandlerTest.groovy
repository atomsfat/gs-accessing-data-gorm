package iamedu.raml

import iamedu.api.annotations.ApiQueryParam
import iamedu.raml.exception.RamlResponseValidationException
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import services.SongsService
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

  }

  def "Handle without service"() {
    setup:
    appContext.containsBean(_) >> { false }
    when:
    RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
    RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, false)
    ResponseEntity<String> res = handler.handle(request, response)
    println "RamlApiHandlerTest.handle $res"
    then:
    res != null
    res.statusCode == HttpStatus.OK
    res.body != null

  }

  def "Handle with service"() {
    setup:

    SongsService songsService = Mock(SongsService)
    songsService.get(_) >> { args -> "hola ${args}" }

    def methods = songsService.class.getMethods().grep {
      it.name == "get"
    }
    println "-----> $methods"

    appContext.containsBean(_) >> { true }
    appContext.getBean(_) >> { songsService }

    when:
    RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
    RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, false)
    ResponseEntity<String> res = handler.handle(request, response)
    println "RamlApiHandlerTest.handleWithService $res"
    then:
    res != null
    res.statusCode == HttpStatus.OK
    res.body.toString() == '"hola"'
  }

  def "Handle with service strictMode"() {
    setup:

    SongsService songsService = Mock(SongsService)

    songsService.get() >> { 'hola' }
    appContext.containsBean(_) >> { true }
    appContext.getBean(_) >> { songsService }

    when:
    RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
    RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, true)
    ResponseEntity<String> res = handler.handle(request, response)
    println "RamlApiHandlerTest.handleWithService $res"
    then:
    thrown RamlResponseValidationException

  }


}

