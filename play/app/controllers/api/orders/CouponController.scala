package controllers.api.orders

import models.{Basket, Coupon, CouponRepository}
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CouponController @Inject()(couponRepository: CouponRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getCoupons:Action[AnyContent] = Action.async { implicit request => couponRepository.list().map(coupons => Ok(
    Json.toJson(coupons)
  ))
  }

  def getCouponsById(id: Int) : Action[AnyContent] = Action.async {
    implicit request => couponRepository.getById(id).map(coupon => Ok(
      Json.toJson(coupon)
    ))
  }

  def getCouponsByCode(code: String) : Action[AnyContent] = Action.async {
    implicit request => couponRepository.getByCode(code).map(coupon => Ok(
      Json.toJson(coupon)
    ))
  }

  def createCoupon:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newCoupon = request.body.as[Coupon]
    val savedCoupon = couponRepository.create(newCoupon.name, newCoupon.code, newCoupon.value)
    savedCoupon.map(coupon => Ok(
      Json.toJson(coupon)
    ))
  }

  def updateCoupon = Action(parse.json) { implicit request: Request[JsValue] =>
    val newCoupon = request.body.as[Coupon]
    couponRepository.update(newCoupon.id, newCoupon)
    Ok("")
  }

  def deleteCoupon(id: Int) = Action {
    couponRepository.delete(id)
    Ok("Deleted")
  }
}
