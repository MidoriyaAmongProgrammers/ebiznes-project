package controllers.products

import javax.inject.{Inject, Singleton}

import models.ReviewRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class ReviewController @Inject()(reviewRepository: ReviewRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getReviews =  Action.async {
    implicit request => reviewRepository.list().map(reviews => Ok(Json.toJson(reviews)))
  }

  def getReviewById(id: Int) =  Action.async {
    implicit request => reviewRepository.getById(id).map(review => Ok(Json.toJson(review)))
  }

  def createReview = Action {
    Ok("")
  }

  def updateReview = Action {
    Ok("")
  }

  def deleteReview(id: Int) = Action {
    Ok("")
  }
}
