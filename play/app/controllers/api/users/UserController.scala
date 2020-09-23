package controllers.api.users

import com.mohiva.play.silhouette.api.Silhouette
import models.{User, UserDao, UserRepository}
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import silhouette.DefaultEnv

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class UserController @Inject()(userDao: UserDao, cc: MessagesControllerComponents, silhouette: Silhouette[DefaultEnv]) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getUsers:Action[AnyContent] = Action.async { implicit request => userDao.getAll().map(users => Ok(
    Json.toJson(users)
  ))
  }

  def getUserById(id: String) : Action[AnyContent] = Action.async {
    implicit request => userDao.getById(id).map(user => Ok(
      Json.toJson(user)
    ))
  }

  def getUserByToken : Action[AnyContent] = silhouette.SecuredAction.async {
    implicit request => userDao.getById(request.identity.id).map(user => Ok(
      Json.toJson(user)
    ))
  }


  def createUser:Action[JsValue] = Action(parse.json).async { implicit request =>
    val newUser = request.body.as[User]
    val savedUser = userDao.create(User("", newUser.firstName, newUser.lastName, newUser.email))
    savedUser.map(user => Ok(
      Json.toJson(user)
    ))
  }

  def updateUser = Action(parse.json) { implicit request =>
    val newUser = request.body.as[User]
    userDao.update(newUser)
    Ok("Updated")
  }

  def deleteUser(id: String) = Action {
    userDao.delete(id)
    Ok("Deleted")
  }
}