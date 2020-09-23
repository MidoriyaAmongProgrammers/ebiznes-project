
package controllers.api.basket

import javax.inject.{Inject, Singleton}
import models._
import models.UserProduct
import play.api.libs.json.{JsPath, JsValue, Json}
import play.api.mvc.{request, _}
import com.mohiva.play.silhouette.api.Silhouette
import silhouette.DefaultEnv

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class BasketProductController @Inject()(basketProductRepository: BasketProductRepository,
                                 userRepository: UserRepository,cc: MessagesControllerComponents, silhouette: Silhouette[DefaultEnv]) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getBasketProducts:Action[AnyContent] = Action.async { implicit request => basketProductRepository.list().map(basketProduct => Ok(
    Json.toJson(basketProduct)
  ))
  }

  def getBasketProductById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => basketProductRepository.getById(id).map(basketProduct => Ok(
      Json.toJson(basketProduct)
    ))
  }

  def getUserProductByUserId : Action[AnyContent] = silhouette.SecuredAction.async {
    implicit request =>
      val user = request.identity
      basketProductRepository.getByUser(user.id).map(basketProduct => Ok(
      Json.toJson(basketProduct)
    ))
  }

  def insertToBasketProductsOrIncreaseQuantity:Action[JsValue] = silhouette.SecuredAction.async(parse.json) { request =>
    val user = request.identity
    val newBasketProduct = (request.body \ "product").as[Int]
    val databaseProduct = Await.result(basketProductRepository.getByBasketAndProduct(user.id, newBasketProduct), Duration.Inf)
    databaseProduct match {
      case Some(databaseProduct) =>
        val databaseQuantity = databaseProduct.quantity
        val productToUpdate = databaseProduct.copy(quantity = databaseQuantity + 1, id = 0)
        basketProductRepository.update(databaseProduct.id, productToUpdate);
      case None =>
        basketProductRepository.create(user.id, newBasketProduct, 1)
    }
    Future(Ok)
  }

  def decreaseQuantity:Action[JsValue] = silhouette.SecuredAction.async(parse.json) { request =>
    val user = request.identity
    val newBasketProduct = (request.body \ "product").as[Int]
    val databaseProduct = Await.result(basketProductRepository.getByBasketAndProduct(user.id, newBasketProduct), Duration.Inf)
    databaseProduct match {
      case Some(databaseProduct) =>
        val databaseQuantity = databaseProduct.quantity
        val productToUpdate = databaseProduct.copy(quantity = databaseQuantity - 1, id = 0)
        basketProductRepository.update(databaseProduct.id, productToUpdate);
      case None =>
        Future.successful(NotFound)
    }
    Future(Ok)
  }

  def deleteBasketProductsByUser = silhouette.SecuredAction { implicit request =>
    val user = request.identity
    basketProductRepository.deleteByUser(user.id)
    Ok("Deleted")
  }

  def deleteBasketProduct(id: Int) = Action { implicit request =>
    basketProductRepository.delete(id)
    Ok("Deleted")
  }

}