package controllers.categories


import models.{Category, CategoryRepository, SubCategory, SubCategoryRepository}
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class SubCategoryController @Inject()(subCategoryRepository: SubCategoryRepository,
                                      categoryRepository: CategoryRepository,
                                      cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val subCategoryForm: Form[CreateSubCategoryForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "category" -> number,
    )(CreateSubCategoryForm.apply)(CreateSubCategoryForm.unapply)
  }

  val updateSubCategoryForm: Form[UpdateSubCategoryForm] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "category" -> number,
    )(UpdateSubCategoryForm.apply)(UpdateSubCategoryForm.unapply)
  }

  def getSubCategories =  Action.async {
    implicit request => subCategoryRepository.list().map(subCategories => Ok(views.html.subcategory.subcategories(subCategories)))
  }

  def getSubCategoryById(id: Int) : Action[AnyContent] =  Action.async {
    implicit request => subCategoryRepository.getById(id).map(subCategory => Ok(views.html.subcategory.subcategory(subCategory)))
  }

  def createSubCategory: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val categories = categoryRepository.list()
    categories.map (categories => Ok(views.html.subcategory.subcategoryAdd(subCategoryForm, categories)))
  }

  def updateSubCategory(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val categories :Seq[Category] = Await.result(categoryRepository.list(), Duration.Inf)

    val subcategory = subCategoryRepository.getById(id)
    subcategory.map(subcat => {
      val subcatForm = updateSubCategoryForm.fill(UpdateSubCategoryForm(subcat.id, subcat.name, subcat.description, subcat.category))
      Ok(views.html.subcategory.subcategoryUpdate(subcatForm, categories))
    })
  }

  def updateSubCategoryHandle = Action.async { implicit request =>
    var categ:Seq[Category] = Await.result(categoryRepository.list(), Duration.Inf)

    updateSubCategoryForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.subcategory.subcategoryUpdate(errorForm, categ))
        )
      },
      subcategory => {
        subCategoryRepository.update(subcategory.id, SubCategory(subcategory.id, subcategory.name, subcategory.description, subcategory.category)).map { _ =>
          Redirect(routes.SubCategoryController.updateSubCategory(subcategory.id)).flashing("success" -> "Subcategory updated")
        }
      }
    )

  }

  def createSubCategoryHandle = Action.async { implicit request =>
    var categories: Seq[Category] = Await.result(categoryRepository.list(), Duration.Inf)

    subCategoryForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.subcategory.subcategoryAdd(errorForm, categories))
        )
      },
      subcategory => {
        subCategoryRepository.create(subcategory.name, subcategory.description, subcategory.category).map { _ =>
          Redirect(routes.SubCategoryController.createSubCategory()).flashing("success" -> "Subcategory created")
        }
      }
    )
  }


  def deleteSubCategory(id: Int) = Action {
    subCategoryRepository.delete(id)
    Redirect(controllers.categories.routes.SubCategoryController.getSubCategories())
  }
}

case class CreateSubCategoryForm(name: String, description: String, category: Int)
case class UpdateSubCategoryForm(id: Int, name: String, description: String, category: Int)
