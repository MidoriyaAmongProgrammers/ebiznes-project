
package controllers.basket

import javax.inject.{Inject, Singleton}

import models._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}
import scala.concurrent.duration.Duration

@Singleton
class BasketController @Inject()(basketRepository: BasketRepository,
                                 userDao: UserDao,cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val basketForm: Form[CreateBasketForm] = Form {
    mapping(
      "user" -> nonEmptyText,
    )(CreateBasketForm.apply)(CreateBasketForm.unapply)
  }

  val updateBasketForm: Form[UpdateBasketForm] = Form {
    mapping(
      "id" -> number,
      "user" -> nonEmptyText,
    )(UpdateBasketForm.apply)(UpdateBasketForm.unapply)
  }

  def getBaskets = Action.async {
    implicit request => basketRepository.list().map(baskets => Ok(
      views.html.basket.baskets(baskets)
    ))
  }

  def getBasketById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => basketRepository.getById(id).map(basket => Ok(
      views.html.basket.basket(basket)
    ))
  }

  def createBasket: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val users = userDao.getAll()
    users.map(users => Ok(views.html.basket.basketAdd(basketForm, users)))
  }

  def updateBasket(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    var users: Seq[User] = Seq[User]()
    userDao.getAll().onComplete{
      case Success(usr) => users = usr
      case Failure(_) => print("fail")
    }

    val basket = basketRepository.getById(id)
    basket.map(basket => {
        val basketForm = updateBasketForm.fill(UpdateBasketForm(basket.id, basket.user))

        Ok(views.html.basket.basketUpdate(basketForm, users))
      })
  }

  def updateBasketHandle = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val users: Seq[User] = Await.result(userDao.getAll(), Duration.Inf)


    updateBasketForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.basket.basketUpdate(errorForm, users))
        )
      },
      basket => {
        basketRepository.update(basket.id, Basket(basket.id, basket.user)).map { _ =>
          Redirect(routes.BasketController.updateBasket(basket.id)).flashing("success" -> "Basket updated")
        }
      }
    )
  }
  def createBasketHandle = Action.async { implicit request =>
    val users:Seq[User] = Await.result(userDao.getAll(), Duration.Inf)

    basketForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.basket.basketAdd(errorForm, users))
        )
      },
      basket => {
        basketRepository.create(basket.user).map { _ =>
          Redirect(routes.BasketController.createBasket()).flashing("success" -> "Basket created")
        }
      }
    )
  }
  def deleteBasket(id: Int) = Action {
    basketRepository.delete(id)
    Redirect(controllers.basket.routes.BasketController.getBaskets())
  }

}

case class CreateBasketForm(user: String)
case class UpdateBasketForm(id: Int, user: String)