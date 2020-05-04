package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class SubCategoryRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, val categoryRepository: CategoryRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class SubCategoryTable(tag: Tag) extends Table[SubCategory](tag, "subcategory") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def description = column[String]("description")

    def category = column[Int]("category")

    def category_fk = foreignKey("category_fk",category, cat)(_.id)

    def * = (id, name, description, category) <> ((SubCategory.apply _).tupled, SubCategory.unapply)

  }

  import categoryRepository.CategoryTable

  private val subcategory = TableQuery[SubCategoryTable]

  private val cat = TableQuery[CategoryTable]


  def create(name: String, description: String, category: Int): Future[SubCategory] = db.run {
    (subcategory.map(p => (p.name, p.description,p.category))
      returning subcategory.map(_.id)
      into {case ((name,description,category),id) => SubCategory(id,name, description,category)}
      ) += (name, description,category)
  }

  def list(): Future[Seq[SubCategory]] = db.run {
    subcategory.result
  }

  def getByCategory(category: Int): Future[Seq[SubCategory]] = db.run {
    subcategory.filter(_.category === category).result
  }

  def getByName(name: String): Future[SubCategory] = db.run {
    subcategory.filter(_.name === name).result.head
  }

  def getById(id: Int): Future[SubCategory] = db.run {
    subcategory.filter(_.id === id).result.head
  }

  def delete(id: Int): Future[Unit] = db.run(subcategory.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newSubcategory: SubCategory): Future[Unit] = {
    val subcategoryToUpdate: SubCategory = newSubcategory.copy(id)
    db.run(subcategory.filter(_.id === id).update(subcategoryToUpdate)).map(_ => ())
  }

}
