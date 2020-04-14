package controllers.orders

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ShipmentsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getShipments = Action {
    Ok("")
  }

  def createShipment = Action {
    Ok("")
  }

  def updateShipment(shipmentId: String) = Action {
    Ok("")
  }

  def deleteShipment(shipmentId: String) = Action {
    Ok("")
  }
}