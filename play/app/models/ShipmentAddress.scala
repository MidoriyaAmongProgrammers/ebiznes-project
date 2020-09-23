package models

import play.api.libs.json.Json

case class ShipmentAddress(id: Int, address: String, city: String, postCode: String)

object ShipmentAddress {
  implicit val shipmentAddressFormat = Json.format[ShipmentAddress]
}
