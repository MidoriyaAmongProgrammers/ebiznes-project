package controllers.api.products

import com.mohiva.play.silhouette.api.Silhouette
import models._
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import silhouette.DefaultEnv

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}


@Singleton
class ReviewController @Inject()(reviewRepository: ReviewRepository,
                                 userRepository: UserRepository,
                                 productRepository: ProductRepository,
                                 silhouette: Silhouette[DefaultEnv],
                                 cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getReviews:Action[AnyContent] = Action.async { implicit request => reviewRepository.list().map(reviews => Ok(
    Json.toJson(reviews)
  ))
  }

  def getReviewById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => reviewRepository.getById(id).map(review => Ok(
      Json.toJson(review)
    ))
  }

  def getReviewByUserAndProduct(id: Int) : Action[AnyContent] = Action.async {
    implicit request => reviewRepository.getById(id).map(review => Ok(
      Json.toJson(review)
    ))
  }

  def createReview:Action[JsValue] = silhouette.SecuredAction.async(parse.json) { implicit request =>
    val user = request.identity
    val newReview = request.body.as[Review]
    val savedReview = reviewRepository.create(user.id, newReview.product, newReview.content, newReview.rating)
    savedReview.map(review => Ok(
      Json.toJson(review)
    ))
  }

  def updateReview = Action(parse.json) { implicit request =>
    val newReview = request.body.as[Review]
    reviewRepository.update(newReview.id, newReview)
    Ok("hashsa")
  }

  def deleteReview(id: Int) = Action {
    reviewRepository.delete(id)
    Ok("Deleted")
  }
}