package models

import java.time.LocalDate
import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShipmentRepository @Inject() (dbConfigProvider: DatabaseConfigProvider,val shipmentAddressRepository: ShipmentAddressRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class ShipmentTable(tag: Tag) extends Table[Shipment](tag, "shipment") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def date = column[String]("date")

    def address = column[Int]("address")

    def firstName = column[String]("firstName")

    def lastName = column[String]("lastName")

    def provider = column[String]("provider")

    def addressFk = foreignKey("address_fk",address, shipmentAddress)(_.id)

    def * = (id, date, address, firstName, lastName, provider) <> ((Shipment.apply _).tupled, Shipment.unapply)

  }

  import shipmentAddressRepository.ShipmentAddressTable


  private val shipment = TableQuery[ShipmentTable]
  private val shipmentAddress = TableQuery[ShipmentAddressTable]

  def create(date: String, address: Int, firstName: String, lastName: String, provider: String): Future[Shipment] = db.run {
    (shipment.map(p => (p.date, p.address, p.firstName, p.lastName, p.provider))
      returning shipment.map(_.id)
      into {case ((date, address, firstName, lastName, provider),id) => Shipment(id, date, address, firstName, lastName, provider)}
      ) += (date, address, firstName, lastName, provider)
  }

  def list(): Future[Seq[Shipment]] = db.run {
    shipment.result
  }

  def getByAddress(address: Int): Future[Shipment] = db.run {
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
