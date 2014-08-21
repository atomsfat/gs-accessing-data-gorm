package services

import iamedu.api.annotations.ApiQueryParam
import iamedu.api.annotations.ApiUrlParam
import org.springframework.stereotype.Service

/**
 * Created by tomas on 8/21/14.
 */
@Service
class SongService {

  String get(@ApiUrlParam("songId") Long id){
    "-->>> $id"
  }
}
