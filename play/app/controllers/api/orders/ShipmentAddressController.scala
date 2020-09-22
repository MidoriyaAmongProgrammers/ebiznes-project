package controllers.api.orders

import models.{Shipment, ShipmentAddress, ShipmentAddressRepository, ShipmentRepository}
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShipmentAddressController @Inject()(shipmentAddressRepository: ShipmentAddressRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global


  def getShipmentAddresses:Action[AnyContent] = Action.async { implicit request => shipmentAddressRepository.list().map(address => Ok(
    Json.toJson(address)
  ))
  }

  def getShipmentAddressById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => shipmentAddressRepository.getById(id).map(address => Ok(
      Json.toJson(address)
    ))
  }

  def createShipmentAddress:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newShipment = request.body.as[ShipmentAddress]
    val savedShipmentAddress = shipmentAddressRepository.create(newShipment.address, newShipment.city, newShipment.postCode)
    savedShipmentAddress.map(address => Ok(
      Json.toJson(address)
    ))
  }

  def updateShipmentAddress = Action(parse.json) { implicit request =>
    val newShipmentAdd = request.body.as[ShipmentAddress]
    val savedShipmentAdd = shipmentAddressRepository.update(newShipmentAdd.id, newShipmentAdd)
    Ok("saas")
  }

  def deleteShipmentAddress(id: Int) = Action {
    shipmentAddressRepository.delete(id)
    Ok("Deleted")
  }
}