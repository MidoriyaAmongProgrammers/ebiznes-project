package controllers.orders

import models.{Shipment, ShipmentRepository}
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShipmentController @Inject()(shipmentRepository: ShipmentRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val shipmentForm: Form[CreateShipmentForm] = Form {
    mapping(
      "date" -> nonEmptyText,
      "address" -> nonEmptyText,
    )(CreateShipmentForm.apply)(CreateShipmentForm.unapply)
  }

  val updateShipmentForm: Form[UpdateShipmentForm] = Form {
    mapping(
      "id" -> number,
      "date" -> nonEmptyText,
      "address" -> nonEmptyText,
    )(UpdateShipmentForm.apply)(UpdateShipmentForm.unapply)
  }

  def getShipments =  Action.async {
    implicit request => shipmentRepository.list().map(shipments => Ok(views.html.shipment.shipments(shipments)))
  }

  def getShipmentById(id: Int) =  Action.async {
    implicit request => shipmentRepository.getById(id).map(shipment => Ok(views.html.shipment.shipment(shipment)))
  }

  def createShipment: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    Future.successful {
      Ok(views.html.shipment.shipmentAdd(shipmentForm))
    }
  }

  def updateShipment(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val shipment = shipmentRepository.getById(id)
    shipment.map(shipment => {
      val shipmentForm = updateShipmentForm.fill(UpdateShipmentForm(shipment.id, shipment.date, shipment.address))
      Ok(views.html.shipment.shipmentUpdate(shipmentForm))
    })
  }

  def updateShipmentHandle = Action.async { implicit request =>
    updateShipmentForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.shipment.shipmentUpdate(errorForm))
        )
      },
      shipment => {
        shipmentRepository.update(shipment.id, Shipment(shipment.id, shipment.date, shipment.address)).map { _ =>
          Redirect(routes.ShipmentController.updateShipment(shipment.id)).flashing("success" -> "Shipment updated")
        }
      }
    )

  }

  def createShipmentHandle = Action.async { implicit request =>

    shipmentForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.shipment.shipmentAdd(errorForm))
        )
      },
      shipment => {
        shipmentRepository.create(shipment.date, shipment.address).map { _ =>
          Redirect(routes.ShipmentController.createShipment()).flashing("success" -> "Shipment created")
        }
      }
    )

  }


  def deleteShipment(id: Int) = Action {
    shipmentRepository.delete(id)
    Redirect(controllers.orders.routes.ShipmentController.getShipments())
  }
}
case class CreateShipmentForm(date: String, address: String)
case class UpdateShipmentForm(id: Int, date: String, address: String)