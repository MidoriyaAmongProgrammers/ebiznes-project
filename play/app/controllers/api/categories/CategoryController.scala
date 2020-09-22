package controllers.api.categories

import models.{Basket, Category, CategoryRepository}
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CategoryController @Inject()(categoryRepository: CategoryRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getCategories:Action[AnyContent] = Action.async { implicit request => categoryRepository.list().map(categories => Ok(
    Json.toJson(categories)
  ))
  }

  def getCategoryById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => categoryRepository.getById(id).map(category => Ok(
      Json.toJson(category)
    ))
  }

  def createCategory:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newCategory = request.body.as[Category]
    val savedCategory = categoryRepository.create(newCategory.name)
    savedCategory.map(category => Ok(
      Json.toJson(category)
    ))
  }

  def updateCategory:Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val newCategory = request.body.as[Category]
    val savedCategory = categoryRepository.update(newCategory.id, newCategory)
    Ok("")
  }

  def deleteCategory(id: Int) = Action {
    categoryRepository.delete(id)
    Ok("Deleted")
  }
}