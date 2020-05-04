package controllers.categories


import models.{Category, CategoryRepository}
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CategoryController @Inject()(categoryRepository: CategoryRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val categoryForm: Form[CreateCategoryForm] = Form {
    mapping(
      "name" -> nonEmptyText,
    )(CreateCategoryForm.apply)(CreateCategoryForm.unapply)
  }

  val updateCategoryForm: Form[UpdateCategoryForm] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
    )(UpdateCategoryForm.apply)(UpdateCategoryForm.unapply)
  }

  def getCategories =  Action.async {
    implicit request => categoryRepository.list().map(categories =>  Ok(views.html.category.categories(categories)))
  }

  def getCategoryById(id: Int) : Action[AnyContent] =  Action.async {
    implicit request => categoryRepository.getById(id).map(category => Ok(views.html.category.category(category)))
  }

  def createCategory: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    Future.successful {
      Ok(views.html.category.categoryAdd(categoryForm))
    }
  }

  def updateCategory(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    val category = categoryRepository.getById(id)
    category.map(cat => {
      val catForm = updateCategoryForm.fill(UpdateCategoryForm(cat.id, cat.name))
      Ok(views.html.category.categoryUpdate(catForm))
    })
  }

  def updateCategoryHandle = Action.async { implicit request =>

    updateCategoryForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.category.categoryUpdate(errorForm))
        )
      },
      category => {
        categoryRepository.update(category.id, Category(category.id, category.name)).map { _ =>
          Redirect(routes.CategoryController.updateCategory(category.id)).flashing("success" -> "Category updated")
        }
      }
    )

  }

  def createCategoryHandle = Action.async { implicit request =>
    categoryForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.category.categoryAdd(errorForm))
        )
      },
      category => {
        categoryRepository.create(category.name).map { _ =>
          Redirect(controllers.categories.routes.CategoryController.createCategory()).flashing("success" -> "Category created")
        }
      }
    )

  }


  def deleteCategory(id: Int) = Action {
    categoryRepository.delete(id)
    Redirect(controllers.categories.routes.CategoryController.getCategories())
  }
}
case class CreateCategoryForm(name: String)
case class UpdateCategoryForm(id: Int, name: String)