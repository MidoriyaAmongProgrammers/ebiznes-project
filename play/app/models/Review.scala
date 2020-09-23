package models

import play.api.libs.json.Json

case class Review(id: Int, user: String, product: Int, content: String, rating: Int)

object Review {
  implicit val reviewFormat = Json.format[Review]
}
