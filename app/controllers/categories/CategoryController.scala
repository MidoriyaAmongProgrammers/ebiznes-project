package controllers.categories

import javax.inject.{Inject, Singleton}

import models.CategoryRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class CategoryController @Inject()(categoryRepository: CategoryRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getCategories =  Action.async {
    implicit request => categoryRepository.list().map(categories => Ok(Json.toJson(categories)))
  }

  def getCategoryById(id: Int) =  Action.async {
    implicit request => categoryRepository.getById(id).map(category => Ok(Json.toJson(category)))
  }

  def createCategory = Action {
    Ok("")
  }

  def updateCategory = Action {
    Ok("")
  }

  def deleteCategory(id: Int) = Action {
    Ok("")
  }
}
