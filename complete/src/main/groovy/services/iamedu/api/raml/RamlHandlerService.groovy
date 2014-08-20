package services.iamedu.api.raml

import iamedu.raml.validator.ApiValidator
import iamedu.raml.validator.ApiValidatorBuilder

class RamlHandlerService {

  ApiValidator validator

  String ramlDefinition
  Boolean reloadRaml


  def doBuildValidator(def ramlDefinition) {
    if(!ramlDefinition) {
      throw new RuntimeException("Raml definition is not set")
    }
    ApiValidatorBuilder builder = new ApiValidatorBuilder()
    builder.setRamlLocation(ramlDefinition)

    builder.build()
  }

  def buildValidator() {

    if(!validator || reloadRaml) {
      validator = doBuildValidator(ramlDefinition)
    }

    validator
  }

}
