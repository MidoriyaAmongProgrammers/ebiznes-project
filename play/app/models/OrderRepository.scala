package models

import java.time.LocalDate
import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository @Inject() (dbConfigProvider: DatabaseConfigProvider,val shipmentRepository: ShipmentRepository,
                                 val paymentRepository: PaymentRepository,val userRepository: UserRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class OrderTable(tag: Tag) extends Table[Order](tag, "order") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def date = column[String]("date")

    def coupon = column[Int]("coupon")

    def shipment = column[Int]("shipment")

    def payment = column[Int]("payment")

    def user = column[String]("user")

    def shipment_fk = foreignKey("shipment_fk",shipment, shipmentQuery)(_.id)

    def payment_fk = foreignKey("payment_fk",payment, paymentQuery)(_.id)

    def user_fk = foreignKey("user_fk",user, userQuery)(_.id)

    def * = (id, date, coupon, shipment, payment, user) <> ((Order.apply _).tupled, Order.unapply)
  }

  import shipmentRepository.ShipmentTable
  import paymentRepository.PaymentTable
  import userRepository.UserTable

  private val order = TableQuery[OrderTable]
  private val shipmentQuery = TableQuery[ShipmentTable]
  private val paymentQuery = TableQuery[PaymentTable]
  private val userQuery = TableQuery[UserTable]


  def create(date: String, coupon: Int, shipment: Int, payment: Int, user: String): Future[Order] = db.run {
    (order.map(p => (p.date, p.coupon, p.shipment, p.payment, p.user))
      returning order.map(_.id)
      into {case ((date, coupon, shipment, payment, user),id) => Order(id,date,coupon,shipment,payment,user)}
      ) += (date,coupon,shipment,payment,user)
  }

  def list(): Future[Seq[Order]] = db.run {
    order.result
  }

  def getByUser(user: String): Future[Seq[Order]] = db.run {
    order.filter(_.user === user).result
  }

  def getById(id: Int): Future[Order] = db.run {
    order.filter(_.id === id).result.head
  }

  def getByDate(date: String): Future[Seq[Order]] = db.run {
    order.filter(_.date === date).result
  }

  def delete(id: Int): Future[Unit] = db.run(order.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newOrder: Order): Future[Unit] = {
    val orderToUpdate: Order = newOrder.copy(id)
    db.run(order.filter(_.id === id).update(orderToUpdate)).map(_ => ())
  }

}
