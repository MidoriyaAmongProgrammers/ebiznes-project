package controllers.products

import javax.inject.{Inject, Singleton}

import models.ProductRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class ProductController @Inject()(productRepository: ProductRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getProducts =  Action.async {
    implicit request => productRepository.list().map(products => Ok(Json.toJson(products)))
  }

  def getProductById(id: Int) = Action.async {
    implicit request => productRepository.getById(id).map(product => Ok(Json.toJson(product)))
  }

  def createProduct = Action {
    Ok("")
  }

  def updateProduct = Action {
    Ok("")
  }

  def deleteProduct(id: Int) = Action {
    Ok("")
  }
}
