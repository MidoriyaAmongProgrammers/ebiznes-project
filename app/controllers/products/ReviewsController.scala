package controllers.products

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ReviewsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getReviews = Action {
    Ok("")
  }

  def createReview = Action {
    Ok("")
  }

  def updateReview(reviewId: String) = Action {
    Ok("")
  }

  def deleteReview(reviewId: String) = Action {
    Ok("")
  }
}
