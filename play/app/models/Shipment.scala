package models

import play.api.libs.json.Json

case class Shipment(id: Int, date: String, address: Int, firstName: String, lastName: String, provider: String)

object Shipment {
  implicit val shipmentFormat = Json.format[Shipment]
}
