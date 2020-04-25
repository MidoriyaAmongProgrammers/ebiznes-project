package models

import play.api.libs.json.Json

case class User(id: Int, username: String, password: String, email: String)

object User {
  implicit val reviewFormat = Json.format[User]
}
