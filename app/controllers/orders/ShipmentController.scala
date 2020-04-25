package controllers.orders

import javax.inject.{Inject, Singleton}

import models.ShipmentRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class ShipmentController @Inject()(shipmentRepository: ShipmentRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getShipments =  Action.async {
    implicit request => shipmentRepository.list().map(shipments => Ok(Json.toJson(shipments)))
  }

  def getShipmentById(id: Int) =  Action.async {
    implicit request => shipmentRepository.getById(id).map(shipment => Ok(Json.toJson(shipment)))
  }

  def createShipment = Action {
    Ok("")
  }

  def updateShipment = Action {
    Ok("")
  }

  def deleteShipment(id: Int) = Action {
    Ok("")
  }
}