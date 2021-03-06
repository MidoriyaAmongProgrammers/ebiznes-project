package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class BasketRepository @Inject() (dbConfigProvider: DatabaseConfigProvider,val userRepository: UserRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class BasketTable(tag: Tag) extends Table[Basket](tag, "basket") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def user = column[String]("user")

    def userFk = foreignKey("user_fk",user, userQuery)(_.id)

    def * = (id, user) <> ((Basket.apply _).tupled, Basket.unapply)

  }


  import userRepository.UserTable

  private val basket = TableQuery[BasketTable]
  private val userQuery = TableQuery[UserTable]

  def create(user: String): Future[Basket] = db.run {
    (basket.map(p => (p.user))
      returning basket.map(_.id)
      into {case ((user),id) => Basket(id,user)}
      ) += (user)
  }

  def list(): Future[Seq[Basket]] = db.run {
    basket.result
  }

  def getById(id: Int): Future[Basket] = db.run {
    basket.filter(_.id === id).result.head
  }

  def getByUser(user: String): Future[Basket] = db.run {
    basket.filter(_.user === user).result.head
  }

  def delete(id: Int): Future[Unit] = db.run(basket.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newBasket: Basket): Future[Unit] = {
    val basketToUpdate: Basket = newBasket.copy(id)
    db.run(basket.filter(_.id === id).update(basketToUpdate)).map(_ => ())
  }

}
