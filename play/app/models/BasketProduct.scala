package models

import play.api.libs.json.Json

case class UserProduct(id: Int, user: String, product: Int, quantity: Int)


object UserProduct {
  implicit val basketProductFormat = Json.format[UserProduct]
}
