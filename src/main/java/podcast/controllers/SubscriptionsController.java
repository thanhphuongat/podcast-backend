package podcast.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import podcast.models.entities.subscriptions.Subscription;
import podcast.models.entities.users.User;
import podcast.models.formats.Failure;
import podcast.models.formats.Result;
import podcast.models.formats.Success;
import podcast.services.SubscriptionsService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import static podcast.utils.Constants.*;

/**
 * Podcast series subscriptions REST API controller
 */
@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionsController {

  private final SubscriptionsService subscriptionsService;

  @Autowired
  public SubscriptionsController(SubscriptionsService subscriptionsService) {
    this.subscriptionsService = subscriptionsService;
  }

  /** Create a subscription **/
  @RequestMapping(method = RequestMethod.POST, value = "/{series_id}")
  public ResponseEntity<Result> createSubscription(HttpServletRequest request,
                                                     @PathVariable("series_id") Long seriesId) {
    User user = (User) request.getAttribute(USER);
    try {
      Subscription subscription = subscriptionsService.createSubscription(user, seriesId);
      return ResponseEntity.status(200).body(new Success(SUBSCRIPTION, subscription));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body(new Failure(e.getMessage()));
    }
  }

  /** Get subscriptions of a particular series (paginated) **/
  @RequestMapping(method = RequestMethod.GET, value = "/{series_id}")
  public ResponseEntity<Result> getSubscriptions(HttpServletRequest request,
                                                 @PathVariable("series_id") Long seriesId,
                                                 @RequestParam("offset") Integer offset,
                                                 @RequestParam("max") Integer max) {
    try {
      List<Subscription> subscriptions = subscriptionsService.getSubscriptions(seriesId, offset, max);
      return ResponseEntity.status(200).body(new Success(SUBSCRIPTIONS, subscriptions));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body(new Failure(e.getMessage()));
    }
  }

  /** Delete a subscription **/
  @RequestMapping(method = RequestMethod.DELETE, value = "/{series_id}")
  public ResponseEntity<Result> deleteSubscription(HttpServletRequest request,
                                                   @PathVariable("series_id") Long seriesId) {
    User user = (User) request.getAttribute(USER);
    try {
      Subscription subscription = subscriptionsService.deleteSubscription(user, seriesId);
      return ResponseEntity.status(200).body(new Success(SUBSCRIPTION, subscription));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body(new Failure(e.getMessage()));
    }
  }

  /** Get a series of subscriptions of a user **/
  @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
  public ResponseEntity<Result> getUserSubscriptions(HttpServletRequest request,
                                                     @PathVariable("id") String userId) {
    try {
      List<Subscription> subs = subscriptionsService.getUserSubscriptions(userId);
      return ResponseEntity.status(200).body(new Success(SUBSCRIPTIONS, subs));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body(new Failure(e.getMessage()));
    }
  }
}
