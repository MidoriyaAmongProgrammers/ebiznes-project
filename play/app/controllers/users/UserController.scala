package controllers.users

import models.{User, UserDao, UserRepository}
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class UserController @Inject()(userDao: UserDao, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  implicit val ec = ExecutionContext.global

  val userForm: Form[CreateUserForm] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> nonEmptyText,
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  val updateUserForm: Form[UpdateUserForm] = Form {
    mapping(
      "id" -> nonEmptyText,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> nonEmptyText,
    )(UpdateUserForm.apply)(UpdateUserForm.unapply)
  }

  def getUsers =  Action.async {
    implicit request => userDao.getAll().map(users => Ok(views.html.user.users(users)))
  }

  def getUserById(id: String) =  Action.async {
    implicit request => userDao.getById(id).map(user => Ok(views.html.user.user(user)))
  }

  def createUser: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    Future.successful {
      Ok(views.html.user.userAdd(userForm))
    }
  }

  def updateUser(id: String): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    val user = userDao.getById(id)
    user.map(user => {
      val prodForm = updateUserForm.fill(UpdateUserForm(user.id, user.firstName, user.lastName, user.email))
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
        userDao.update(User(user.id, user.firstName, user.lastName, user.email)).map { _ =>
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
        userDao.create(User("", user.firstName, user.lastName, user.email)).map { _ =>
          Redirect(routes.UserController.createUser()).flashing("success" -> "User created")
        }
      }
    )

  }


  def deleteUser(id: String) = Action {
    userDao.delete(id)
    Redirect(controllers.users.routes.UserController.getUsers())
  }
}
case class CreateUserForm(firstName: String, lastName: String, email: String)
case class UpdateUserForm(id: String, firstName: String, lastName: String, email: String)