package controllers.users

import models.{User, UserRepository}
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class UserController @Inject()(userRepository: UserRepository, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val userForm: Form[CreateUserForm] = Form {
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "email" -> nonEmptyText,
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  val updateUserForm: Form[UpdateUserForm] = Form {
    mapping(
      "id" -> number,
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "email" -> nonEmptyText,
    )(UpdateUserForm.apply)(UpdateUserForm.unapply)
  }

  def getUsers =  Action.async {
    implicit request => userRepository.list().map(users => Ok(views.html.user.users(users)))
  }

  def getUserById(id: Int) =  Action.async {
    implicit request => userRepository.getById(id).map(user => Ok(views.html.user.user(user)))
  }

  def createUser: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    Future.successful {
      Ok(views.html.user.userAdd(userForm))
    }
  }

  def updateUser(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    val user = userRepository.getById(id)
    user.map(user => {
      val prodForm = updateUserForm.fill(UpdateUserForm(user.id, user.username, user.password, user.email))
      Ok(views.html.user.userUpdate(prodForm))
    })
  }

  def updateUserHandle = Action.async { implicit request =>
    updateUserForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.user.userUpdate(errorForm))
        )
      },
      user => {
        userRepository.update(user.id, User(user.id, user.username, user.password, user.email)).map { _ =>
          Redirect(routes.UserController.updateUser(user.id)).flashing("success" -> "User updated")
        }
      }
    )

  }

  def createUserHandle = Action.async { implicit request =>
    userForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.user.userAdd(errorForm))
        )
      },
      user => {
        userRepository.create(user.username, user.password, user.email).map { _ =>
          Redirect(routes.UserController.createUser()).flashing("success" -> "User created")
        }
      }
    )

  }


  def deleteUser(id: Int) = Action {
    userRepository.delete(id)
    Redirect(controllers.users.routes.UserController.getUsers())
  }
}
case class CreateUserForm(username: String, password: String, email: String)
case class UpdateUserForm(id: Int, username: String, password: String, email: String)