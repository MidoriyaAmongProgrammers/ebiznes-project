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

    def user = column[String]("user")

    def product = column[Int]("product")

    def content = column[String]("content")

    def rating = column[Int]("rating")

    def userFk = foreignKey("user_fk",user, userQuery)(_.id)

    def productFk = foreignKey("product_fk",product, productQuery)(_.id)

    def * = (id, user, product, content, rating) <> ((Review.apply _).tupled, Review.unapply)

  }

  import userRepository.UserTable
  import productRepository.ProductTable

  private val review = TableQuery[ReviewTable]

  private val userQuery = TableQuery[UserTable]

  private val productQuery = TableQuery[ProductTable]

  def create(user: String, product: Int, content: String, rating: Int): Future[Review] = db.run {
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

  def getByProductAndUser(productId: Int, userId: String): Future[Seq[Review]] = db.run {
    review.filter(x => x.user === userId && x.product === productId).result
  }

  def getById(id: Int): Future[Review] = db.run {
    review.filter(_.id === id).result.head
  }

  def delete(id: Int): Future[Unit] = db.run(review.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newReview: Review): Future[Unit] = {
    val reviewToUpdate: Review = newReview.copy(id)
    db.run(review.filter(_.id === id).update(reviewToUpdate)).map(_ => ())
  }
}
