package models

import play.api.libs.json.Json

case class Product(id: Int, name: String, price : Float, description: String, subCategory: Int, img: String)

object Product {
  implicit val productFormat = Json.format[Product]
}
