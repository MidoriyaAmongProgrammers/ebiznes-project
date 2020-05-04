package controllers.orders


import models.{Coupon, CouponRepository}
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CouponController @Inject()(couponRepository: CouponRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val couponForm: Form[CreateCouponForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "code" -> nonEmptyText,
      "value" -> number,
    )(CreateCouponForm.apply)(CreateCouponForm.unapply)
  }

  val updateCouponForm: Form[UpdateCouponForm] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "code" -> nonEmptyText,
      "value" -> number,
    )(UpdateCouponForm.apply)(UpdateCouponForm.unapply)
  }

  def getCoupons =  Action.async {
    implicit request => couponRepository.list().map(coupons => Ok(views.html.coupon.coupons(coupons)))
  }

  def getCouponsById(id: Int) =  Action.async {
    implicit request => couponRepository.getById(id).map(coupon => Ok(views.html.coupon.coupon(coupon)))
  }

  def createCoupon: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    Future.successful {
      Ok(views.html.coupon.couponAdd(couponForm))
    }
  }

  def updateCoupon(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    val coupon = couponRepository.getById(id)
    coupon.map(coupon => {
      val couponForm = updateCouponForm.fill(UpdateCouponForm(coupon.id, coupon.name, coupon.code, coupon.value))
      Ok(views.html.coupon.couponUpdate(couponForm))
    })
  }

  def updateCouponHandle = Action.async { implicit request =>

    updateCouponForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.coupon.couponUpdate(errorForm))
        )
      },
      coupon => {
        couponRepository.update(coupon.id, Coupon(coupon.id, coupon.name, coupon.code, coupon.value)).map { _ =>
          Redirect(routes.CouponController.updateCoupon(coupon.id)).flashing("success" -> "Coupon updated")
        }
      }
    )

  }

  def createCouponHandle = Action.async { implicit request =>
    couponForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.coupon.couponAdd(errorForm))
        )
      },
      coupon => {
        couponRepository.create(coupon.name, coupon.code, coupon.value).map { _ =>
          Redirect(routes.CouponController.createCoupon()).flashing("success" -> "Coupon created")
        }
      }
    )
  }

  def deleteCoupon(id: Int) = Action {
    couponRepository.delete(id)
    Redirect(controllers.orders.routes.CouponController.getCoupons())
  }
}
case class CreateCouponForm(name: String, code: String, value: Int)
case class UpdateCouponForm(id: Int, name: String, code: String, value: Int)
