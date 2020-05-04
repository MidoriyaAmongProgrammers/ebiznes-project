package controllers.orders

import models._
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class OrderController @Inject()(orderRepository: OrderRepository,
                                couponRepository: CouponRepository,
                                userRepository: UserRepository,
                                paymentRepository: PaymentRepository,
                                shipmentRepository: ShipmentRepository,
                                cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val orderForm: Form[CreateOrderForm] = Form {
    mapping(
      "date" -> nonEmptyText,
      "coupon" -> number,
      "shipment" -> number,
      "payment" -> number,
      "user" -> number,
    )(CreateOrderForm.apply)(CreateOrderForm.unapply)
  }

  val updateOrderForm: Form[UpdateOrderForm] = Form {
    mapping(
      "id" -> number,
      "date" -> nonEmptyText,
      "coupon" -> number,
      "shipment" -> number,
      "payment" -> number,
      "user" -> number,
    )(UpdateOrderForm.apply)(UpdateOrderForm.unapply)
  }

  def getOrders =  Action.async {
    implicit request => orderRepository.list().map(orders => Ok(views.html.order.orders(orders)))
  }

  def getOrderById(id: Int) =  Action.async {
    implicit request => orderRepository.getById(id).map(order => Ok(views.html.order.order(order)))
  }

  def createOrder: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val users: Seq[User] = Await.result(userRepository.list(), Duration.Inf)
    val shipments: Seq[Shipment] = Await.result(shipmentRepository.list(), Duration.Inf)
    val coupons: Seq[Coupon] = Await.result(couponRepository.list(), Duration.Inf)
    val payments: Seq[Payment] = Await.result(paymentRepository.list(), Duration.Inf)

    Future.successful {
      Ok(views.html.order.orderAdd(orderForm, coupons, shipments, payments, users))
    }
  }

  def updateOrder(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val users: Seq[User] = Await.result(userRepository.list(), Duration.Inf)
    val shipments: Seq[Shipment] = Await.result(shipmentRepository.list(), Duration.Inf)
    val coupons: Seq[Coupon] = Await.result(couponRepository.list(), Duration.Inf)
    val payments: Seq[Payment] = Await.result(paymentRepository.list(), Duration.Inf)


    val order = orderRepository.getById(id)
    order.map(order => {
      val orderForm = updateOrderForm.fill(UpdateOrderForm(order.id, order.date, order.coupon, order.shipment, order.payment, order.user))
      Ok(views.html.order.orderUpdate(orderForm, coupons, shipments, payments, users))
    })
  }

  def updateOrderHandle = Action.async { implicit request =>
    val users: Seq[User] = Await.result(userRepository.list(), Duration.Inf)
    val shipments: Seq[Shipment] = Await.result(shipmentRepository.list(), Duration.Inf)
    val coupons: Seq[Coupon] = Await.result(couponRepository.list(), Duration.Inf)
    val payments: Seq[Payment] = Await.result(paymentRepository.list(), Duration.Inf)

    updateOrderForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.order.orderUpdate(errorForm, coupons, shipments, payments, users))
        )
      },
      order => {
        orderRepository.update(order.id, Order(order.id, order.date, order.coupon, order.shipment, order.payment, order.user)).map { _ =>
          Redirect(routes.OrderController.updateOrder(order.id)).flashing("success" -> "Order updated")
        }
      }
    )

  }

  def createOrderHandle = Action.async { implicit request =>
    val users: Seq[User] = Await.result(userRepository.list(), Duration.Inf)
    val shipments: Seq[Shipment] = Await.result(shipmentRepository.list(), Duration.Inf)
    val coupons: Seq[Coupon] = Await.result(couponRepository.list(), Duration.Inf)
    val payments: Seq[Payment] = Await.result(paymentRepository.list(), Duration.Inf)

    orderForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.order.orderAdd(errorForm, coupons, shipments, payments, users))
        )
      },
      order => {
        orderRepository.create(order.date, order.coupon, order.shipment, order.payment, order.user).map { _ =>
          Redirect(routes.OrderController.createOrder()).flashing("success" -> "Order created")
        }
      }
    )

  }


  def deleteOrder(id: Int) = Action {
    orderRepository.delete(id)
    Redirect(controllers.orders.routes.OrderController.getOrders())
  }
}

case class CreateOrderForm(date: String, coupon: Int, shipment: Int, payment: Int, user: Int)
case class UpdateOrderForm(id: Int, date: String, coupon: Int, shipment: Int, payment: Int, user: Int)
