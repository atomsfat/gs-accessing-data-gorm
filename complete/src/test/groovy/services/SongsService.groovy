package services

import iamedu.api.annotations.ApiQueryParam


/**
 * Created by tomas on 8/21/14.
 */
interface SongsService {

  String get(@ApiQueryParam("query") String query)

}
