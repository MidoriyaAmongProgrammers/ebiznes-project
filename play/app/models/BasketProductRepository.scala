package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BasketProductRepository @Inject() (dbConfigProvider: DatabaseConfigProvider,val userRepository: UserRepository,val productRepository: ProductRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class UserProductTable(tag: Tag) extends Table[UserProduct](tag, "user_product") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def user = column[String]("user")

    def product = column[Int]("product")

    def quantity = column[Int]("quantity")

    def userFk = foreignKey("user_fk",user, userQuery)(_.id)

    def productFk = foreignKey("product_fk",product, productQuery)(_.id)

    def * = (id, user, product, quantity) <> ((UserProduct.apply _).tupled, UserProduct.unapply)

  }


  import productRepository.ProductTable
  import userRepository.UserTable

  private val userProduct = TableQuery[UserProductTable]
  private val userQuery = TableQuery[UserTable]
  private val productQuery = TableQuery[ProductTable]

  def create(user: String, product: Int, quantity: Int): Future[UserProduct] = db.run {
    (userProduct.map(p => (p.user, p.product, p.quantity))
      returning userProduct.map(_.id)
      into {case ((user, product, quantity),id) => UserProduct(id, user, product, quantity)}
      ) += (user, product, quantity)
  }

  def list(): Future[Seq[UserProduct]] = db.run {
    userProduct.result
  }

  def getById(id: Int): Future[Option[UserProduct]] = db.run {
    userProduct.filter(_.id === id).result.headOption
  }

  def getByUser(user: String): Future[Seq[UserProduct]] = db.run {
    userProduct.filter(_.user === user).result
  }

  def getByBasketAndProduct(user: String, product: Int): Future[Option[UserProduct]] = db.run {
    userProduct.filter(x => x.user === user && x.product === product).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(userProduct.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newUserProduct: UserProduct): Future[Unit] = {
    val userProductToUpdate: UserProduct = newUserProduct.copy(id)
    db.run(userProduct.filter(_.id === id).update(userProductToUpdate)).map(_ => ())
  }

}
