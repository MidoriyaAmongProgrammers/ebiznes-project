package models

import play.api.libs.json.Json

case class Payment(id: Int, value: Int, paid : Boolean)

object Payment {
  implicit val paymentFormat = Json.format[Payment]
}
