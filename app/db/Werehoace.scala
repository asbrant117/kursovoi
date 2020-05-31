package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class WarehouseType(id: Int,
                         s: Int,
                         Warehouce: String,
                         capacity: Int,
                        )


class WarehouseRepository {

  val GenreQuery: TableQuery[WarehouseTypes] = TableQuery[WarehouseTypes]

  def insert(user: WarehouseType): Future[Int] =
    db.run(GenreQuery += user)

  def get(id: Int): Future[Option[WarehouseType]] =
    db.run(
      GenreQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[WarehouseType]] =
    db.run(
      GenreQuery
        .result
    )

}

private[db] class WarehouseTypes(tag: Tag) extends Table[WarehouseType](tag, "Warehouce") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def sityId: Rep[Int] = column[Int]("sityId")

  def Warehouse: Rep[String] = column[String]("Warehouse")

  def capacity: Rep[Int] = column[Int]("capacity")


  def * : ProvenShape[WarehouseType] = (id, sityId, Warehouse, capacity) <> (WarehouceType.tupled, WarehouceType.unapply) // scalastyle:ignore

}



//  def getdanger(id: Int): Future[UserProfile] =
//    db.run(
//      userProfileQuery
//        //        .filter { it => it.Name === "Name" }
//        //        .filter { it => it.Name like "%Ru" }
//        .filter { it => it.id === id }
//        .take(1)
//        .result
//        .head)

//  def delete(id: Int): Future[Int] =
//    db.run(userProfileQuery.filter(_.id === id).delete)

//  def update(id: Int, Population: Int): Future[Int] =
//    db.run(
//      CountryQuery
//        .filter(_.id === id)
//        .map(_.country)
//        .update(country))