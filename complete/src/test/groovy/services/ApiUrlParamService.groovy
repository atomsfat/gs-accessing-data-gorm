package services

import iamedu.api.annotations.ApiQueryParam
import iamedu.api.annotations.ApiUrlParam

/**
 * Created by tomas on 8/25/14.
 */
public interface ApiUrlParamService {

  String get(@ApiUrlParam("songId") String search)

}