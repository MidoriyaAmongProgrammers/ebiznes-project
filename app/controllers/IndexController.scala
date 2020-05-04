
package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class IndexController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getIndex = Action {
    implicit request =>  Ok(views.html.index())
  }

}
