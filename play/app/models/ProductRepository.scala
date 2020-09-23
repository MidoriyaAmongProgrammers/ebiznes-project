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

    def img = column[String]("img")


    def subcategoryFk = foreignKey("subcat_fk",subcategory, subcat)(_.id)


    def * = (id, name, price, description, subcategory, img) <> ((Product.apply _).tupled, Product.unapply)

  }


  import subCategoryRepository.SubCategoryTable

  private val product = TableQuery[ProductTable]

  private val subcat = TableQuery[SubCategoryTable]



  def create(name: String, price: Float, description: String, subcategory: Int, img: String): Future[Product] = db.run {
    (product.map(p => (p.name, p.price, p.description, p.subcategory, p.img))
      returning product.map(_.id)
      into {case ((name, price, description, subcategory, img),id) => Product(id, name, price, description, subcategory, img)}
      ) += (name, price, description, subcategory, img)
  }


  def list(): Future[Seq[Product]] = db.run {
    product.result
  }

  def getBySubCategory(subCategory_id: Int): Future[Seq[Product]] = db.run {
    product.filter(_.subcategory === subCategory_id).result
  }

  def getByCategories(subcategory_ids: Seq[Int]): Future[Seq[Product]] = db.run {
    product.filter(_.subcategory inSet subcategory_ids).result
  }

  def getById(id: Int): Future[Product] = db.run {
    product.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Product]] = db.run {
    product.filter(_.id === id).result.headOption
  }

  def update(id: Int, newProduct: Product): Future[Unit] = {
    val productToUpdate: Product = newProduct.copy(id)
    db.run(product.filter(_.id === id).update(productToUpdate)).map(_ => ())
  }

  def delete(id: Int): Future[Unit] = db.run(product.filter(_.id === id).delete).map(_ => ())



}

