package config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import iamedu.raml.RamlHandlerService

/**
 * Created by tomas on 8/20/14.
 */
@Configuration
class ApiConfig {

  @Bean
  @Qualifier("sample-api")
  RamlHandlerService getSampleApi() {
    new RamlHandlerService(ramlDefinition:  "raml/jukebox-api.raml", reloadRaml: true)
  }
}
