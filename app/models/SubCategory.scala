package models

import play.api.libs.json.Json

case class SubCategory(id: Int, name: String, description: String, category: Int)

object SubCategory {
  implicit val subCategoryFormat = Json.format[SubCategory]
}
