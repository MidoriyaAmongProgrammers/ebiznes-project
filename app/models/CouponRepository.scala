package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class CouponRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class CouponTable(tag: Tag) extends Table[Coupon](tag, "coupon") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def code = column[String]("code")

    def value = column[Int]("value")

    def * = (id, name, code, value) <> ((Coupon.apply _).tupled, Coupon.unapply)

  }

  private val coupon = TableQuery[CouponTable]

  def create(name: String, code: String, value: Int): Future[Coupon] = db.run {
    (coupon.map(p => (p.name, p.code,p.value))
      returning coupon.map(_.id)
      into {case ((name,code,value),id) => Coupon(id,name, code,value)}
      ) += (name, code,value)
  }

  def list(): Future[Seq[Coupon]] = db.run {
    coupon.result
  }

  def getByName(name: String): Future[Coupon] = db.run {
    coupon.filter(_.name === name).result.head
  }

  def getById(id: Int): Future[Coupon] = db.run {
    coupon.filter(_.id === id).result.head
  }

  def getByCode(code: String): Future[Coupon] = db.run {
    coupon.filter(_.code === code).result.head
  }
}