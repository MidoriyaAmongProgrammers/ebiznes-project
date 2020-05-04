package controllers.products

import models._
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}


@Singleton
class ReviewController @Inject()(reviewRepository: ReviewRepository,
                                 userRepository: UserRepository,
                                 productRepository: ProductRepository,
                                 cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val reviewForm: Form[CreateReviewForm] = Form {
    mapping(
      "user" -> number,
      "product" -> number,
      "content" -> nonEmptyText,
      "rating" -> number,
    )(CreateReviewForm.apply)(CreateReviewForm.unapply)
  }

  val updateReviewForm: Form[UpdateReviewForm] = Form {
    mapping(
      "id" -> number,
      "user" -> number,
      "product" -> number,
      "content" -> nonEmptyText,
      "rating" -> number,
    )(UpdateReviewForm.apply)(UpdateReviewForm.unapply)
  }

  def getReviews =  Action.async {
    implicit request => reviewRepository.list().map(reviews => Ok(views.html.review.reviews(reviews)))
  }

  def getReviewById(id: Int) =  Action.async {
    implicit request => reviewRepository.getById(id).map(review => Ok(views.html.review.review(review)))
  }

  def createReview: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val users: Seq[User] = Await.result(userRepository.list(), Duration.Inf)
    val products: Seq[Product] = Await.result(productRepository.list(), Duration.Inf)
    Future.successful {
    Ok(views.html.review.reviewAdd(reviewForm, users, products))
    }
  }

  def updateReview(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val users: Seq[User] = Await.result(userRepository.list(), Duration.Inf)
    val products: Seq[Product] = Await.result(productRepository.list(), Duration.Inf)
    val review = reviewRepository.getById(id)
    review.map(review => {
      val reviewForm = updateReviewForm.fill(UpdateReviewForm(review.id, review.user, review.product, review.content, review.rating))
      Ok(views.html.review.reviewUpdate(reviewForm, users, products))
    })
  }

  def updateReviewHandle = Action.async { implicit request =>
    var users: Seq[User] = Seq[User]()
    userRepository.list().onComplete{
      case Success(usr) => users = usr
      case Failure(_) => print("fail")
    }

    var products: Seq[Product] = Seq[Product]()
    productRepository.list().onComplete{
      case Success(prod) => products = prod
      case Failure(_) => print("fail")
    }

    updateReviewForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.review.reviewUpdate(errorForm, users, products))
        )
      },
      review => {
        reviewRepository.update(review.id, Review(review.id, review.user, review.product, review.content, review.rating)).map { _ =>
          Redirect(routes.ReviewController.updateReview(review.id)).flashing("success" -> "Review updated")
        }
      }
    )

  }

  def createReviewHandle = Action.async { implicit request =>
    val users: Seq[User] = Await.result(userRepository.list(), Duration.Inf)
    val products: Seq[Product] = Await.result(productRepository.list(), Duration.Inf)

    reviewForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.review.reviewAdd(errorForm, users, products))
        )
      },
      review => {
        reviewRepository.create(review.user, review.product, review.content, review.rating).map { _ =>
          Redirect(routes.ReviewController.createReview()).flashing("success" -> "Review created")
        }
      }
    )

  }


  def deleteReview(id: Int) = Action {
    reviewRepository.delete(id)
    Redirect(controllers.products.routes.ReviewController.getReviews())
  }
}
case class CreateReviewForm(user: Int, product: Int, content: String, rating: Int)
case class UpdateReviewForm(id: Int, user: Int, product: Int, content: String, rating: Int)