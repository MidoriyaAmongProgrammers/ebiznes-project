package controllers.api.orders

import models.{Shipment, ShipmentRepository}
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShipmentController @Inject()(shipmentRepository: ShipmentRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global


  def getShipments:Action[AnyContent] = Action.async { implicit request => shipmentRepository.list().map(shipments => Ok(
    Json.toJson(shipments)
  ))
  }

  def getShipmentById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => shipmentRepository.getById(id).map(shipment => Ok(
      Json.toJson(shipment)
    ))
  }

  def createShipment:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newShipment = request.body.as[Shipment]
    val savedShipment = shipmentRepository.create(newShipment.date, newShipment.address, newShipment.firstName, newShipment.lastName, newShipment.provider)
    savedShipment.map(shipment => Ok(
      Json.toJson(shipment)
    ))
  }

  def updateShipment = Action(parse.json) { implicit request =>
    val newShipment = request.body.as[Shipment]
    shipmentRepository.update(newShipment.id, newShipment)
    Ok("saas")
  }

  def deleteShipment(id: Int) = Action {
    shipmentRepository.delete(id)
    Ok("Deleted")
  }
}