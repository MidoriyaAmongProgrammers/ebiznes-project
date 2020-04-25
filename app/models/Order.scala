package models

import play.api.libs.json.Json

case class Order(id: Int, date: String, coupon: Int, shipment: Int, payment: Int, user: Int)

object Order {
  implicit val orderFormat = Json.format[Order]
}
