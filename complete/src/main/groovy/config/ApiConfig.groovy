package config

import iamedu.raml.RamlApiHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import iamedu.raml.RamlHandlerService

/**
 * Created by tomas on 8/20/14.
 */
@Configuration
class ApiConfig {

  @Autowired
  private ApplicationContext appContext

  @Value('${api.raml.reload}')
  private Boolean reloadRaml

  @Value('${api.raml.testRamlDefinitionPath}')
  private String ramlDefinition

  @Value('${api.raml.serveExamples}')
  private Boolean serveExamples

  @Value('${api.raml.strictMode}')
  private Boolean strictMode

  @Bean
  @Qualifier("testApiHandler")
  RamlApiHandler ramlApiHandler(){
    RamlHandlerService ramlHandlerService =  new RamlHandlerService(ramlDefinition: ramlDefinition, reloadRaml: reloadRaml)
    new RamlApiHandler(appContext:appContext, ramlHandlerService:ramlHandlerService, serveExamples : true, strictMode:false)
  }
}
