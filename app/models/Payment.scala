package models

import play.api.libs.json.Json

case class Payment(id: Int, value: Float, paid : Boolean)

object Payment {
  implicit val paymentFormat = Json.format[Payment]
}
