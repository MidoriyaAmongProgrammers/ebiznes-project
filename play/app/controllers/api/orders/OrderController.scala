package controllers.api.orders

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

@Singleton
class OrderController @Inject()(orderRepository: OrderRepository,
                                couponRepository: CouponRepository,
                                userRepository: UserRepository,
                                paymentRepository: PaymentRepository,
                                shipmentRepository: ShipmentRepository,
                                silhouette: Silhouette[DefaultEnv],
                                cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getOrders:Action[AnyContent] = Action.async { implicit request => orderRepository.list().map(orders => Ok(
    Json.toJson(orders)
  ))
  }

  def getOrderById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => orderRepository.getById(id).map(order => Ok(
      Json.toJson(order)
    ))
  }

  def getOrderByToken : Action[AnyContent] = silhouette.SecuredAction.async {
    implicit request =>
      var user = request.identity
      orderRepository.getByUser(user.id).map(order => Ok(
      Json.toJson(order)
    ))
  }

  def createOrder:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newOrder = request.body.as[Order]
    var date = "2020-09-23"
    val savedOrder = orderRepository.create(date, newOrder.coupon, newOrder.shipment, newOrder.payment, newOrder.user)
    savedOrder.map(order => Ok(
      Json.toJson(order)
    ))
  }

  def updateOrder:Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val newOrder = request.body.as[Order]
    val savedOrder = orderRepository.update(newOrder.id, newOrder)
    Ok("")
  }

  def deleteOrder(id: Int) = Action {
    orderRepository.delete(id)
    Ok("Deleted")
  }
}
