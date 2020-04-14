package controllers.categories

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class SubCategoriesController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getSubCategories = Action {
    Ok("")
  }

  def createSubCategory = Action {
    Ok("")
  }

  def updateSubCategory(categoryId: String) = Action {
    Ok("")
  }

  def deleteSubCategory(categoryId: String) = Action {
    Ok("")
  }
}
