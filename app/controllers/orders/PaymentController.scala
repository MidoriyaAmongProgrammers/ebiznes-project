package controllers.orders

import models.{Payment, PaymentRepository}
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentController @Inject()(paymentRepository: PaymentRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val paymentForm: Form[CreatePaymentForm] = Form {
    mapping(
      "value" -> number,
      "paid" -> boolean,
    )(CreatePaymentForm.apply)(CreatePaymentForm.unapply)
  }

  val updatePaymentForm: Form[UpdatePaymentForm] = Form {
    mapping(
      "id" -> number,
      "value" -> number,
      "paid" -> boolean,
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
      val payForm = updatePaymentForm.fill(UpdatePaymentForm(payment.id, payment.value, payment.paid))
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
        paymentRepository.update(payment.id, Payment(payment.id, payment.value, payment.paid)).map { _ =>
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
        paymentRepository.create(payment.value, payment.paid).map { _ =>
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
case class CreatePaymentForm(value: Int, paid: Boolean)
case class UpdatePaymentForm(id: Int, value: Int, paid: Boolean)