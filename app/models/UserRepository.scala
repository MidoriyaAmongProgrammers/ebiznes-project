package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, subCategoryRepository: SubCategoryRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class UserTable(tag: Tag) extends Table[User](tag, "user") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def username = column[String]("username")

    def password = column[String]("password")

    def email = column[String]("email")

    def * = (id, username, password, email) <> ((User.apply _).tupled, User.unapply)

  }

  private val user = TableQuery[UserTable]


  def create(username: String, password: String, email: String): Future[User] = db.run {
    (user.map(p => (p.username, p.password, p.email))
      returning user.map(_.id)
      into {case ((username, password, email),id) => User(id, username, password, email)}
      ) += (username, password, email)
  }

  def list(): Future[Seq[User]] = db.run {
    user.result
  }

  def getByUserName(username: String): Future[User] = db.run {
    user.filter(_.username === username).result.head
  }

  def getById(id: Int): Future[User] = db.run {
    user.filter(_.id === id).result.head
  }

  def getByEmail(email: String): Future[User] = db.run {
    user.filter(_.email === email).result.head
  }

}

