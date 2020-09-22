package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class UserTable(tag: Tag) extends Table[User](tag, "user") {
    def id = column[String]("Id", O.PrimaryKey, O.Unique)

    def email = column[String]("Email", O.Unique)

    def firstName = column[String]("FirstName")

    def lastName = column[String]("LastName")

    def * = (id, email, firstName, lastName) <> ((User.apply _).tupled, User.unapply)
  }

  private val user = TableQuery[UserTable]


}

