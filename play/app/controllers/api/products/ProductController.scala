package controllers.api.products

import models._
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.data.format.Formats._
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class ProductController @Inject()(productRepository: ProductRepository,
                                  categoryRepository: CategoryRepository,
                                  subCategoryRepository: SubCategoryRepository,
                                  cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getProducts :Action[AnyContent] = Action.async { implicit request => productRepository.list().map(products => Ok(
    Json.toJson(products)
  ))
  }

  def getProductById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => productRepository.getById(id).map(product => Ok(
      Json.toJson(product)
    ))
  }

  def getProductBySubcategory(id: Int) : Action[AnyContent] = Action.async {
    implicit request => productRepository.getBySubCategory(id).map(product => Ok(
      Json.toJson(product)
    ))
  }

  def getProductByCategory(id: Int) : Action[AnyContent] = Action.async {
    implicit request =>
      var subcategories = Await.result(subCategoryRepository.getByCategory(id), Duration.Inf).map(subcat => subcat.id)
      productRepository.getByCategories(subcategories).map(product => Ok(
      Json.toJson(product)
    ))
  }

  def createProduct:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newProduct = request.body.as[Product]
    val savedProduct =
      productRepository.create(newProduct.name, newProduct.price, newProduct.description, newProduct.subCategory, newProduct.img)
    savedProduct.map(product => Ok(
      Json.toJson(product)
    ))
  }

  def updateProduct = Action(parse.json) { implicit request =>
    val newProduct = request.body.as[Product]
    productRepository.update(newProduct.id, newProduct)
    Ok("ashhas")
  }

  def deleteProduct(id: Int) = Action {
    productRepository.delete(id)
    Ok("Deleted")
  }
}