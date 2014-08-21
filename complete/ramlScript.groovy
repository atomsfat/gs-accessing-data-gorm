@Grab(group = "org.raml", module = "raml-parser", version = "0.8.6")
import org.raml.model.Raml
import org.raml.parser.visitor.RamlDocumentBuilder

Raml raml = new RamlDocumentBuilder().build("src/main/resources/raml/jukebox-api.raml")

println raml.getResource("/songs").is
println raml.getResource("/songs").getUriParameters()