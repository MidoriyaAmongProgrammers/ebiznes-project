package controllers.api.orders

import models.{Payment, PaymentRepository}
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentController @Inject()(paymentRepository: PaymentRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getPayments:Action[AnyContent] = Action.async { implicit request => paymentRepository.list().map(payments => Ok(
    Json.toJson(payments)
  ))
  }

  def getPaymentById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => paymentRepository.getById(id).map(payment => Ok(
      Json.toJson(payment)
    ))
  }

  def createPayment:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newPayment = request.body.as[Payment]
    val savedPayment = paymentRepository.create(newPayment.value, newPayment.paid, newPayment.method)
    savedPayment.map(payment => Ok(
      Json.toJson(payment)
    ))
  }

  def updatePayment = Action(parse.json) { implicit request =>
    val newPayment = request.body.as[Payment]
    paymentRepository.update(newPayment.id, newPayment)
    Ok("")
  }

  def deletePayment(id: Int) = Action {
    paymentRepository.delete(id)
    Ok("Deleted")
  }
}