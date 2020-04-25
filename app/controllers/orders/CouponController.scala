package controllers.orders

import javax.inject.{Inject, Singleton}

import models.CouponRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class CouponController @Inject()(couponRepository: CouponRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getCoupons =  Action.async {
    implicit request => couponRepository.list().map(coupons => Ok(Json.toJson(coupons)))
  }

  def getCouponsById(id: Int) =  Action.async {
    implicit request => couponRepository.getById(id).map(coupon => Ok(Json.toJson(coupon)))
  }

  def createCoupon = Action {
    Ok("")
  }

  def updateCoupon = Action {
    Ok("")
  }

  def deleteCoupon(id: Int) = Action {
    Ok("")
  }
}
