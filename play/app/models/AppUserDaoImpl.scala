package models

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext


@Singleton
class AppUserDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, userRepository: UserRepository)
                              (implicit ec: ExecutionContext)
  extends AppUserDao {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import userRepository.UserTable

  private val userTable = TableQuery[UserTable]
  private val loginInfoTable = TableQuery[LoginInfoTable]
  private val userLoginInfoTable = TableQuery[UserLoginInfoTable]


  def save(user: UserIdentity) = db.run {
    val dbUser = User(user.id, user.email, user.firstName, user.lastName)
    userTable.insertOrUpdate(dbUser).map(_ => user)
  }

  def update(user: UserIdentity) = db.run {
    val dbUser = User(user.id, user.email, user.firstName, user.lastName)
    userTable.filter(_.id === user.id).update(dbUser).map(_ => user)
  }

  def find(loginInfo: LoginInfo) = {
    val findLoginInfoQuery = loginInfoTable.filter(dbLoginInfo =>
      dbLoginInfo.providerId === loginInfo.providerID &&  dbLoginInfo.providerKey === loginInfo.providerKey)
    val query = for {
      dbLoginInfo <- findLoginInfoQuery
      dbUserLoginInfo <- userLoginInfoTable.filter(_.loginInfoId === dbLoginInfo.id)
      dbUser <- userTable.filter(_.id === dbUserLoginInfo.userId)
    } yield dbUser
    db.run(query.result.headOption).map { dbUserOption =>
      dbUserOption.map { user =>
        UserIdentity(user.id, user.email, user.firstName, user.lastName)
      }
    }
  }
}