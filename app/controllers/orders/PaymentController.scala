package controllers.orders

import javax.inject.{Inject, Singleton}

import models.PaymentRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class PaymentController @Inject()(paymentRepository: PaymentRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getPayments =  Action.async {
    implicit request => paymentRepository.list().map(payments => Ok(Json.toJson(payments)))
  }

  def getPaymentById(id: Int) =  Action.async {
    implicit request => paymentRepository.getById(id).map(payment => Ok(Json.toJson(payment)))
  }

  def createPayment = Action {
    Ok("")
  }

  def updatePayment = Action {
    Ok("")
  }

  def deletePayment(id: Int) = Action {
    Ok("")
  }
}
