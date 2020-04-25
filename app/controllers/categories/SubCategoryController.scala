package controllers.categories

import javax.inject.{Inject, Singleton}

import models.SubCategoryRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class SubCategoryController @Inject()(subCategoryRepository: SubCategoryRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getSubCategories =  Action.async {
    implicit request => subCategoryRepository.list().map(subCategories => Ok(Json.toJson(subCategories)))
  }

  def getSubCategoryById(id: Int) =  Action.async {
    implicit request => subCategoryRepository.getById(id).map(subCategory => Ok(Json.toJson(subCategory)))
  }

  def createSubCategory = Action {
    Ok("")
  }

  def updateSubCategory = Action {
    Ok("")
  }

  def deleteSubCategory(id: Int) = Action {
    Ok("")
  }
}
