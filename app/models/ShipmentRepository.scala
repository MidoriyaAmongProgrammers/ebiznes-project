package models

import java.time.LocalDate
import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShipmentRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class ShipmentTable(tag: Tag) extends Table[Shipment](tag, "shipment") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def date = column[String]("date")

    def address = column[String]("address")

    def * = (id, date, address) <> ((Shipment.apply _).tupled, Shipment.unapply)

  }

  private val shipment = TableQuery[ShipmentTable]

  def create(date: String, address: String): Future[Shipment] = db.run {
    (shipment.map(p => (p.date, p.address))
      returning shipment.map(_.id)
      into {case ((date, address),id) => Shipment(id, date, address)}
      ) += (date, address)
  }

  def list(): Future[Seq[Shipment]] = db.run {
    shipment.result
  }

  def getByAddress(address: String): Future[Shipment] = db.run {
    shipment.filter(_.address === address).result.head
  }

  def getById(id: Int): Future[Shipment] = db.run {
    shipment.filter(_.id === id).result.head
  }

  def delete(id: Int): Future[Unit] = db.run(shipment.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newShipment: Shipment): Future[Unit] = {
    val shipmentToUpdate: Shipment = newShipment.copy(id)
    db.run(shipment.filter(_.id === id).update(shipmentToUpdate)).map(_ => ())
  }
}
