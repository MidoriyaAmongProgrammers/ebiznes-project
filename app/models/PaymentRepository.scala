package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class PaymentRepository @Inject() (dbConfigProvider: DatabaseConfigProvider, categoryRepository: CategoryRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class PaymentTable(tag: Tag) extends Table[Payment](tag, "payment") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def value = column[Int]("value")

    def paid = column[Boolean]("paid")


    def * = (id, value, paid) <> ((Payment.apply _).tupled, Payment.unapply)

  }

  private val payment = TableQuery[PaymentTable]

  def create(value: Int, paid: Boolean): Future[Payment] = db.run {
    (payment.map(p => (p.value, p.paid))
      returning payment.map(_.id)
      into {case ((value,paid),id) => Payment(id,value, paid)}
      ) += (value, paid)
  }

  def list(): Future[Seq[Payment]] = db.run {
    payment.result
  }

  def getByPaid(paid: Boolean): Future[Seq[Payment]] = db.run {
    payment.filter(_.paid === paid).result
  }

  def getById(id: Int): Future[Payment] = db.run {
    payment.filter(_.id === id).result.head
  }

  def delete(id: Int): Future[Unit] = db.run(payment.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newPayment: Payment): Future[Unit] = {
    val paymentToUpdate: Payment = newPayment.copy(id)
    db.run(payment.filter(_.id === id).update(paymentToUpdate)).map(_ => ())
  }

}
