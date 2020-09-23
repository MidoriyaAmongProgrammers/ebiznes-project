
package controllers.api.basket

import javax.inject.{Inject, Singleton}
import models._
import play.api.libs.json.{JsSuccess, JsValue, Json}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class BasketController @Inject()(basketRepository: BasketRepository,
                                 userRepository: UserRepository,cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getBaskets:Action[AnyContent] = Action.async { implicit request => basketRepository.list().map(baskets => Ok(
      Json.toJson(baskets)
    ))
  }

  def getBasketById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => basketRepository.getById(id).map(basket => Ok(
      Json.toJson(basket)
    ))
  }

  def createBasket:Action[JsValue] = Action(parse.json).async { request =>
    val newBasket = request.body.as[Basket]
    val savedBasket = basketRepository.create(newBasket.user)
    savedBasket.map(basket => Ok(
      Json.toJson(basket)
    ))
  }

  def updateBasket:Action[JsValue] = Action(parse.json) { implicit request =>
    val newBasket = request.body.as[Basket]
    basketRepository.update(newBasket.id, newBasket);
    Ok("Updated")
  }

  def deleteBasket(id: Int) = Action { implicit request =>
    basketRepository.delete(id)
    Ok("Deleted")
  }

}