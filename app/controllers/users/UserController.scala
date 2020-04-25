package controllers.users

import javax.inject.{Inject, Singleton}

import models.UserRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = ExecutionContext.global

  def getUsers =  Action.async {
    implicit request => userRepository.list().map(users => Ok(Json.toJson(users)))
  }

  def getUserById(id: Int) =  Action.async {
    implicit request => userRepository.getById(id).map(users => Ok(Json.toJson(users)))
  }

  def createUser = Action {
    Ok("")
  }

  def updateUser = Action {
    Ok("")
  }

  def deleteUser(id: Int) = Action {
    Ok("")
  }
}
