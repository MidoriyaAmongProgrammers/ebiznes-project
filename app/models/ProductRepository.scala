package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class ProductRepository @Inject() (dbConfigProvider: DatabaseConfigProvider,val subCategoryRepository: SubCategoryRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class ProductTable(tag: Tag) extends Table[Product](tag, "product") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def price = column[Float]("price")

    def description = column[String]("description")

    def subcategory = column[Int]("subcategory")

    def subcategory_fk = foreignKey("subcat_fk",subcategory, subcat)(_.id)


    def * = (id, name, price, description, subcategory) <> ((Product.apply _).tupled, Product.unapply)

  }


  import subCategoryRepository.SubCategoryTable

  private val product = TableQuery[ProductTable]

  private val subcat = TableQuery[SubCategoryTable]



  def create(name: String, price: Float, description: String, subcategory: Int): Future[Product] = db.run {
    (product.map(p => (p.name, p.price, p.description, p.subcategory))
      returning product.map(_.id)
      into {case ((name, price, description, subcategory),id) => Product(id, name, price, description, subcategory)}
      ) += (name, price, description, subcategory)
  }


  def list(): Future[Seq[Product]] = db.run {
    product.result
  }

  def getBySubCategory(category_id: Int): Future[Seq[Product]] = db.run {
    product.filter(_.subcategory === category_id).result
  }

  def getById(id: Int): Future[Product] = db.run {
    product.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Product]] = db.run {
    product.filter(_.id === id).result.headOption
  }

  def getByCategories(subcategory_ids: List[Int]): Future[Seq[Product]] = db.run {
    product.filter(_.subcategory inSet subcategory_ids).result
  }

  def update(id: Int, newProduct: Product): Future[Unit] = {
    val productToUpdate: Product = newProduct.copy(id)
    db.run(product.filter(_.id === id).update(productToUpdate)).map(_ => ())
  }

  def delete(id: Int): Future[Unit] = db.run(product.filter(_.id === id).delete).map(_ => ())



}

