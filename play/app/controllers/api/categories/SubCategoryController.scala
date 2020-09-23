package controllers.api.categories

import models.{Basket, Category, CategoryRepository, SubCategory, SubCategoryRepository}
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class SubCategoryController @Inject()(subCategoryRepository: SubCategoryRepository,
                                      categoryRepository: CategoryRepository,
                                      cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global


  def getSubCategories:Action[AnyContent] = Action.async { implicit request => subCategoryRepository.list().map(subCategories => Ok(
    Json.toJson(subCategories)
  ))
  }

  def getSubCategoryById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => subCategoryRepository.getById(id).map(subCategory => Ok(
      Json.toJson(subCategory)
    ))
  }

  def getSubCategoryByCategoryId(id: Int) : Action[AnyContent] = Action.async {
    implicit request => subCategoryRepository.getByCategory(id).map(subCategories => Ok(
      Json.toJson(subCategories)
    ))
  }

  def createSubCategory:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newSubCat = request.body.as[SubCategory]
    val savedSubCat = subCategoryRepository.create(newSubCat.name, newSubCat.description, newSubCat.category)
    savedSubCat.map(subCategory => Ok(
      Json.toJson(subCategory)
    ))
  }

  def updateSubCategory:Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val newSubCat = request.body.as[SubCategory]
    subCategoryRepository.update(newSubCat.id, newSubCat)
    Ok("sa")
  }

  def deleteSubCategory(id: Int) = Action {
    subCategoryRepository.delete(id)
    Ok("Deleted")
  }
}
