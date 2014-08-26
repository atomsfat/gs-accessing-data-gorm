package services

import iamedu.api.annotations.ApiHeaderParam

/**
 * Created by tomas on 8/26/14.
 */
public interface ApiHeaderParamService {
  String get(@ApiHeaderParam("X-Auth-Token") String token)
}