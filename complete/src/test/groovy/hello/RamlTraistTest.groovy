package hello

import org.raml.model.Raml
import org.raml.parser.visitor.RamlDocumentBuilder
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by tomas on 8/21/14.
 */
@Ignore
class RamlTraistTest extends Specification {

  void "testing spock works"(){
    given:
      Raml raml = new RamlDocumentBuilder().build("raml/jukebox-api.raml");
      println raml.traits
      println raml.getResource('/songs/{songId}').getUriParameters()
      println raml.getResource('/songs/{songId}').getAction("GET")
      println raml.getResource("/songs").getAction("get").queryParameters
    expect:
      raml.getResource("/songs") != null
  }
}
