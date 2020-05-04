package controllers.products

import models._
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.data.format.Formats._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class ProductController @Inject()(productRepository: ProductRepository,
                                  categoryRepository: CategoryRepository,
                                  subCategoryRepository: SubCategoryRepository,
                                  cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val productForm: Form[CreateProductForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "price" -> of(floatFormat),
      "description" -> nonEmptyText,
      "subCategory" -> number,
    )(CreateProductForm.apply)(CreateProductForm.unapply)
  }

  val updateProductForm: Form[UpdateProductForm] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "price" -> of(floatFormat),
      "description" -> nonEmptyText,
      "subCategory" -> number,
    )(UpdateProductForm.apply)(UpdateProductForm.unapply)
  }

  def getProducts =  Action.async {
    implicit request => productRepository.list().map(products => Ok(views.html.product.products(products)))
  }

  def getProductById(id: Int) = Action.async {
    implicit request => productRepository.getById(id).map(product => Ok(views.html.product.product(product)))
  }

  def createProduct: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val subcategories: Seq[SubCategory] = Await.result(subCategoryRepository.list(), Duration.Inf)
    Future.successful{ Ok(views.html.product.productAdd(productForm, subcategories))}
  }

  def updateProduct(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    val subcategories: Seq[SubCategory] = Await.result(subCategoryRepository.list(), Duration.Inf)

    val product = productRepository.getById(id)
    product.map(product => {
      val prodForm = updateProductForm.fill(UpdateProductForm(product.id, product.name, product.price, product.description,product.subCategory))
      Ok(views.html.product.productUpdate(prodForm, subcategories))
    })
  }

  def updateProductHandle = Action.async { implicit request =>
    var subcategories: Seq[SubCategory] = Await.result(subCategoryRepository.list(), Duration.Inf)

    updateProductForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.product.productUpdate(errorForm, subcategories))
        )
      },
      product => {
        productRepository.update(product.id, Product(product.id, product.name, product.price, product.description, product.subCategory)).map { _ =>
          Redirect(routes.ProductController.updateProduct(product.id)).flashing("success" -> "Product updated")
        }
      }
    )

  }

  def createProductHandle = Action.async { implicit request =>
    var subcategories: Seq[SubCategory] = Await.result(subCategoryRepository.list(), Duration.Inf)

    productForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.product.productAdd(errorForm, subcategories))
        )
      },
      product => {
        productRepository.create(product.name, product.price, product.description, product.subCategory).map { _ =>
          Redirect(routes.ProductController.createProduct()).flashing("success" -> "Product created")
        }
      }
    )
  }

  def deleteProduct(id: Int) = Action {
    productRepository.delete(id)
    Redirect(controllers.products.routes.ProductController.getProducts())
  }
}
case class CreateProductForm(name: String, price: Float, description: String, subCategory: Int)
case class UpdateProductForm(id: Int, name: String, price: Float, description: String, subCategory: Int)