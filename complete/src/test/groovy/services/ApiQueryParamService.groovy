package services

import iamedu.api.annotations.ApiQueryParam


/**
 * Created by tomas on 8/21/14.
 */
interface ApiQueryParamService {
  String get(@ApiQueryParam("query") String query)
}

