package models

import play.api.libs.json.Json

case class User(id: String, email: String, firstName: String, lastName: String)

object User {
  implicit val reviewFormat = Json.format[User]
}
