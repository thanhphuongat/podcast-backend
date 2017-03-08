package podcast.models.entities;

import com.couchbase.client.java.document.json.JsonObject;
import lombok.Getter;
import podcast.models.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Recommended podcast feed element
 */
public class RecommendationFeedElement extends FeedElement {

  @Getter private Constants.Type type = Constants.Type.RECOMMENDATION;
  @Getter private Episode episode;
  @Getter private List<User> users;

  /**
   * Constructor from Episode and initial recommending user
   * @param episode - Episode
   * @param user - User
   */
  public RecommendationFeedElement(Episode episode, User user) {
    this.episode = episode;
    this.users = new ArrayList<User>();
    this.users.add(user);
  }


  /**
   * Add user to to this recommendation feed element
   * @param user - User
   */
  public void addUser(User user) {
    this.users.add(user);
  }


  /**
   * See {@link Entity#toJsonObject()}
   */
  public JsonObject toJsonObject() {
    // TODO
    return null;
  }


}