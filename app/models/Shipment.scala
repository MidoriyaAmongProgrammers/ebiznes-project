package models

import java.time.LocalDate

import play.api.libs.json.Json

case class Shipment(id: Int, date: String, address: String)

object Shipment {
  implicit val shipmentFormat = Json.format[Shipment]
}
