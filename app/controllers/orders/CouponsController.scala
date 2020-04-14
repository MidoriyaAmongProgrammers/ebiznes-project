package controllers.orders

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class CouponsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getCoupons = Action {
    Ok("")
  }

  def createCoupon = Action {
    Ok("")
  }

  def updateCoupon(couponId: String) = Action {
    Ok("")
  }

  def deleteCoupon(couponId: String) = Action {
    Ok("")
  }
}
