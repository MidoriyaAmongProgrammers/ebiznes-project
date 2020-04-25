package controllers.orders

import javax.inject.{Inject, Singleton}

import models.OrderRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class OrderController @Inject()(orderRepository: OrderRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getOrders =  Action.async {
    implicit request => orderRepository.list().map(orders => Ok(Json.toJson(orders)))
  }

  def getOrderById(id: Int) =  Action.async {
    implicit request => orderRepository.getById(id).map(order => Ok(Json.toJson(order)))
  }

  def createOrder = Action {
    Ok("")
  }

  def updateOrder = Action {
    Ok("")
  }

  def deleteOrder(id: Int) = Action {
    Ok("")
  }
}
