package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class ReviewRepository @Inject() (dbConfigProvider: DatabaseConfigProvider,val userRepository : UserRepository,
                                  val productRepository: ProductRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class ReviewTable(tag: Tag) extends Table[Review](tag, "review") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def user = column[Int]("user")

    def product = column[Int]("product")

    def content = column[String]("content")

    def rating = column[Int]("rating")

    def user_fk = foreignKey("user_fk",user, userQuery)(_.id)

    def product_fk = foreignKey("product_fk",user, productQuery)(_.id)

    def * = (id, user, product, content, rating) <> ((Review.apply _).tupled, Review.unapply)

  }

  import userRepository.UserTable
  import productRepository.ProductTable

  private val review = TableQuery[ReviewTable]

  private val userQuery = TableQuery[UserTable]

  private val productQuery = TableQuery[ProductTable]

  def create(user: Int, product: Int, content: String, rating: Int): Future[Review] = db.run {
    (review.map(p => (p.user, p.product,p.content, p.rating))
      returning review.map(_.id)
      into {case ((user,product,content,rating),id) => Review(id,user,product,content,rating)}
      ) += (user,product,content,rating)
  }

  def list(): Future[Seq[Review]] = db.run {
    review.result
  }

  def getByProduct(product: Int): Future[Seq[Review]] = db.run {
    review.filter(_.product === product).result
  }

  def getById(id: Int): Future[Review] = db.run {
    review.filter(_.id === id).result.head
  }
}
