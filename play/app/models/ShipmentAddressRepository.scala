package models

import java.time.LocalDate
import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShipmentAddressRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  class ShipmentAddressTable(tag: Tag) extends Table[ShipmentAddress](tag, "shipmentAddress") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def address = column[String]("address")

    def city = column[String]("city")

    def postCode = column[String]("postCode")


    def * = (id, address, city, postCode) <> ((ShipmentAddress.apply _).tupled, ShipmentAddress.unapply)

  }

  private val shipmentAddress = TableQuery[ShipmentAddressTable]

  def create(address: String, city: String, postCode: String): Future[ShipmentAddress] = db.run {
    (shipmentAddress.map(p => (p.address, p.city, p.postCode))
      returning shipmentAddress.map(_.id)
      into {case ((address, city, postCode),id) => ShipmentAddress(id, address, city, postCode)}
      ) += (address, city, postCode)
  }

  def list(): Future[Seq[ShipmentAddress]] = db.run {
    shipmentAddress.result
  }

  def getByAddress(address: String): Future[ShipmentAddress] = db.run {
    shipmentAddress.filter(_.address === address).result.head
  }

  def getById(id: Int): Future[ShipmentAddress] = db.run {
    shipmentAddress.filter(_.id === id).result.head
  }

  def delete(id: Int): Future[Unit] = db.run(shipmentAddress.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newShipmentAddress: ShipmentAddress): Future[Unit] = {
    val shipmentAdressToUpdate: ShipmentAddress = newShipmentAddress.copy(id)
    db.run(shipmentAddress.filter(_.id === id).update(shipmentAdressToUpdate)).map(_ => ())
  }
}

