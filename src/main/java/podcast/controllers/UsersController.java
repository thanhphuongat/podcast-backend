package podcast.controllers;

import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import podcast.services.GoogleService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

  @Autowired
  private GoogleService googleService;

  /**
   * Google Sign In Endpoint
   * @param idToken - Google ID
   * @return - TODO
   */
  @RequestMapping(method = RequestMethod.POST, value = "/google_sign_in")
  public JsonNode googleSignIn(@RequestParam(value="id_token", required = true) String idToken) {
    // TODO - deal with actual user logistics here
    return googleService.googleAuthentication(idToken);
  }

}
