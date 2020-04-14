package controllers.users

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class UsersController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getUsers = Action {
    Ok("")
  }

  def createUser = Action {
    Ok("")
  }

  def updateUser(userId: String) = Action {
    Ok("")
  }

  def deleteUser(userId: String) = Action {
    Ok("")
  }
}
