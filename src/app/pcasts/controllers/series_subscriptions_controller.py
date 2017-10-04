from . import *

class SeriesSubscriptionsController(AppDevController):

  def get_path(self):
    return '/subscriptions/<series_id>/'

  def get_methods(self):
    return ['GET', 'POST', 'DELETE']

  @authorize
  def content(self, **kwargs):
    series_id = request.view_args['series_id']
    user_id = kwargs.get('user').id

    if request.method == "GET":
      offset = request.args['offset']
      max_subs = request.args['max']
      subscriptions = \
        subscriptions_dao.get_series_subscriptions(series_id, user_id,
                                                   max_subs, offset)

      return {'subscriptions': \
        [subscription_schema.dump(s).data for s in subscriptions]}

    elif request.method == "POST":
      subscription = \
        subscriptions_dao.create_subscription(user_id, series_id)

      return {'subscription': subscription_schema.dump(subscription).data}

    elif request.method == "DELETE":
      subscription = \
        subscriptions_dao.delete_subscription(user_id, series_id)

      return {'subscription': subscription_schema.dump(subscription).data}