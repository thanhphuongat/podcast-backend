package podcast.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import podcast.models.entities.Following;
import podcast.models.entities.User;
import podcast.models.formats.Failure;
import podcast.models.formats.Result;
import podcast.models.formats.Success;
import podcast.models.utils.Constants;
import podcast.services.FollowersFollowingsService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/followings")
public class FollowingsController {

  private final FollowersFollowingsService ffService;

  @Autowired
  public FollowingsController(FollowersFollowingsService ffService) {
    this.ffService = ffService;
  }

  /**
   * Create a following.
   * This is the endpoint we want to call when a user follows another.
   *
   * @param request
   * @param id      the user being followed
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, value = "/new")
  public ResponseEntity<Result> newFollowing(HttpServletRequest request,
                                             @RequestParam(value = Constants.ID) String id) {

    /* Grab the user corresponding to the request */
    User user = (User) request.getAttribute(Constants.USER);

    try {
      Following following = ffService.createFollowing(user, id);
      return ResponseEntity.status(200).body(
          new Success(Constants.FOLLOWING, following));
    } catch (Exception e) {
      return ResponseEntity.status(400).body(new Failure(e.getMessage()));
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "/")
  public ResponseEntity<Result> getUserFollowings(HttpServletRequest request,
                                                  @RequestParam(value = Constants.ID) String id) {

    User user = (User) request.getAttribute(Constants.USER);
    Optional<List<Following>> followings;

    if (id.equals("me")) {
      followings = ffService.getUserFollowings(user.getId());
    }
    else {
      followings = ffService.getUserFollowings(id);
    }
      try {
        return ResponseEntity.status(200).body(
            new Success(Constants.FOLLOWINGS, followings.orElse(new ArrayList<Following>())));
      } catch (Exception e) {
        return ResponseEntity.status(400).body(new Failure(e.getMessage()));
      }
  }
}
