package models

import play.api.libs.json.Json

case class Coupon(id: Int, name: String, code: String, value: Int)

object Coupon {
  implicit val couponFormat = Json.format[Coupon]
}
