package iamedu.raml

import iamedu.api.annotations.ApiHeaderParam
import iamedu.api.annotations.ApiQueryParam
import iamedu.api.annotations.ApiUrlParam
import iamedu.raml.exception.RamlResponseValidationException
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import services.ApiHeaderParamService
import services.ApiUrlParamService
import services.ApiQueryParamService
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
      Vector<String> v =  new Vector<String>()
      v.add("accept")
      v.add("X-Auth-Token")
      v.elements()
    }

    request.getHeaders("accept") >> {
      Vector<String> h = new Vector<String>()
      h.add('application/json')
      h.elements()
    }
    request.getHeaders("X-Auth-Token") >> {
      Vector<String> h = new Vector<String>()
      h.add('auth-token')
      h.elements()
    }
    request.getQueryString() >> { "query=metro" }
    request.getParameterMap() >> {
      Map<String, String[]> params = new HashMap<String, String[]>()
      params.put('query', 'metro')
      return params
    }


  }

  def "Handle without service"() {
    setup:
      appContext.containsBean(_) >> { false }
      request.getRequestURI() >> {
        "/sample-api/api/songs"
      }
    when:
      RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
      RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, false)
      ResponseEntity<String> res = handler.handle(request, response)
    then:
      res != null
      res.statusCode == HttpStatus.OK
      res.body != null

  }

  def "Handle with service"() {
    setup:
      ApiQueryParamService songsService = Mock(ApiQueryParamService)
      songsService.get(_) >> { "hola" }
      appContext.containsBean(_) >> { true }
      appContext.getBean(_) >> { songsService }
      request.getRequestURI() >> {
        "/sample-api/api/songs"
      }

    when:
      RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
      RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, false)
      ResponseEntity<String> res = handler.handle(request, response)
    then:
      res != null
      res.statusCode == HttpStatus.OK
      res.body.toString() == '"hola"'
  }

  def "Handle with service strictMode"() {
    setup:

      ApiQueryParamService songsService = Mock(ApiQueryParamService)

      songsService.get() >> { 'hola' }
      appContext.containsBean(_) >> { true }
      appContext.getBean(_) >> { songsService }
      request.getRequestURI() >> {
        "/sample-api/api/songs"
      }

    when:
      RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
      RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, true)
      ResponseEntity<String> res = handler.handle(request, response)
    then:
      thrown RamlResponseValidationException

  }

  def "Handle with service @ApiQueryParam"() {
    setup:
      ApiQueryParamService songsService = new ApiQueryParamService() {
        @Override
        String get(@ApiQueryParam("query") String query) {
          query
        }
      }
      appContext.containsBean(_) >> { true }
      appContext.getBean(_) >> { songsService }
      request.getRequestURI() >> {
        "/sample-api/api/songs"
      }

    when:
      RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
      RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, false)
      ResponseEntity<String> res = handler.handle(request, response)
    then:
      res != null
      res.statusCode == HttpStatus.OK
      res.body.toString() == '"metro"'
  }

  def "Handle with service @ApiUrlParam"() {

    setup:
      ApiUrlParamService songService = new ApiUrlParamService() {
        @Override
        String get(@ApiUrlParam("songId") String songId) {
          return songId
        }
      }
      appContext.containsBean(_) >> { true }
      appContext.getBean(_) >> { songService }

      request.getRequestURI() >> {
        "/sample-api/api/songs/10"
      }

    when:
      println request.getRequestURI()
      RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
      RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, false)
      ResponseEntity<String> res = handler.handle(request, response)
    then:
      res != null
      res.statusCode == HttpStatus.OK
      res.body.toString() == '"10"'
  }

  def "Handle with service @ApiHeaderParam"() {

    setup:
      ApiHeaderParamService songsService = new ApiHeaderParamService() {
        @Override
        String get(@ApiHeaderParam("X-Auth-Token") String token) {
          return token
        }
      }
      appContext.containsBean(_) >> { true }
      appContext.getBean(_) >> { songsService }

      request.getRequestURI() >> {
        "/sample-api/api/songs"
      }

    when:
      println request.getRequestURI()
      RamlHandlerService ramlHandlerService = new RamlHandlerService(ramlDefinition: "raml/jukebox-api.raml", reloadRaml: true)
      RamlApiHandler handler = new RamlApiHandler(appContext, ramlHandlerService, true, false)
      ResponseEntity<String> res = handler.handle(request, response)
    then:
      res != null
      res.statusCode == HttpStatus.OK
      res.body.toString() == '"auth-token"'
  }


}

