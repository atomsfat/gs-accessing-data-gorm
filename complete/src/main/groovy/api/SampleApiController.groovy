package api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by tomas on 8/18/14.
 */
@RestController
class SampleApiController {
  private static final Logger log = LoggerFactory.getLogger(SampleApiController.class)

  @RequestMapping(value = "/sample-api/api/**")
  @ResponseBody
  ResponseEntity<String> handle(HttpServletRequest request, HttpServletResponse response) {

  }


}
