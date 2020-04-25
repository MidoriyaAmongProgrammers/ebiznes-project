
package controllers.basket

import javax.inject.{Inject, Singleton}

import models.BasketRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class BasketController @Inject()(basketRepository: BasketRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getBaskets = Action.async {
    implicit request => basketRepository.list().map(baskets => Ok(Json.toJson(baskets)))
  }

  def getBasketById(id: Int) = Action.async {
    implicit request => basketRepository.getById(id).map(basket => Ok(Json.toJson(basket)))
  }

  def createBasket = Action {
    Ok("")
  }

  def updateBasket = Action {
    Ok("")
  }

  def deleteBasket(id: Int) = Action {
    Ok("")
  }
}