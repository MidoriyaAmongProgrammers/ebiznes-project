package controllers.orders

import models.{Payment, PaymentRepository}
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.data.format.Formats._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentController @Inject()(paymentRepository: PaymentRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val paymentForm: Form[CreatePaymentForm] = Form {
    mapping(
      "value" -> of(floatFormat),
      "paid" -> boolean,
      "method" -> nonEmptyText,
    )(CreatePaymentForm.apply)(CreatePaymentForm.unapply)
  }

  val updatePaymentForm: Form[UpdatePaymentForm] = Form {
    mapping(
      "id" -> number,
      "value" -> of(floatFormat),
      "paid" -> boolean,
      "method" -> nonEmptyText,
    )(UpdatePaymentForm.apply)(UpdatePaymentForm.unapply)
  }

  def getPayments =  Action.async {
    implicit request => paymentRepository.list().map(payments => Ok(views.html.payment.payments(payments)))
  }

  def getPaymentById(id: Int) =  Action.async {
    implicit request => paymentRepository.getById(id).map(payment => Ok(views.html.payment.payment(payment)))
  }

  def createPayment: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    Future.successful {
      Ok(views.html.payment.paymentAdd(paymentForm))
    }
  }

  def updatePayment(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    val payment = paymentRepository.getById(id)
    payment.map(payment => {
      val payForm = updatePaymentForm.fill(UpdatePaymentForm(payment.id, payment.value, payment.paid, payment.method))
      Ok(views.html.payment.paymentUpdate(payForm))
    })
  }

  def updatePaymentHandle = Action.async { implicit request =>
    updatePaymentForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.payment.paymentUpdate(errorForm))
        )
      },
      payment => {
        paymentRepository.update(payment.id, Payment(payment.id, payment.value, payment.paid, payment.method)).map { _ =>
          Redirect(routes.PaymentController.updatePayment(payment.id)).flashing("success" -> "Payment updated")
        }
      }
    )

  }

  def createPaymentHandle = Action.async { implicit request =>
    paymentForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.payment.paymentAdd(errorForm))
        )
      },
      payment => {
        paymentRepository.create(payment.value, payment.paid, payment.method).map { _ =>
          Redirect(routes.PaymentController.createPayment()).flashing("success" -> "Payment created")
        }
      }
    )

  }


  def deletePayment(id: Int) = Action {
    paymentRepository.delete(id)
    Redirect(controllers.orders.routes.PaymentController.getPayments())
  }
}
case class CreatePaymentForm(value: Float, paid: Boolean, method: String)
case class UpdatePaymentForm(id: Int, value: Float, paid: Boolean, method: String)