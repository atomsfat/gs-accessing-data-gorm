package hello

import org.raml.model.Raml
import org.raml.parser.visitor.RamlDocumentBuilder
import spock.lang.Specification

/**
 * Created by tomas on 8/21/14.
 */

class RamlTraistTest extends Specification {

  void "testing spock works"(){
    given:
      Raml raml = new RamlDocumentBuilder().build("raml/jukebox-api.raml");
      println raml.traits
      println raml.getResource('/songs/${songId}').getAction("get").is
    println raml.getResource("/songs").getAction("get").queryParameters
      println raml.getResource("/songs").getDisplayName()
    expect:
      raml.getResource("/songs") != null
  }
}
