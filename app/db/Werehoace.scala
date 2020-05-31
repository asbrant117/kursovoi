package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class WarehouceType(id: Int,
                         cityId: Int,
                         Warehouce: String,
                         capacity: Int,
                        )


class WarehouceRepository {

  val GenreQuery: TableQuery[WarehouceTypes] = TableQuery[WarehouceTypes]

  def insert(user: WarehouceType): Future[Int] =
    db.run(GenreQuery += user)

  def get(id: Int): Future[Option[WarehouceType]] =
    db.run(
      GenreQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[WarehouceType]] =
    db.run(
      GenreQuery
        .result
    )

}

private[db] class WarehouceTypes(tag: Tag) extends Table[WarehouceType](tag, "Genre") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def cityId: Rep[Int] = column[Int]("id")

  def Warehouce: Rep[String] = column[String]("Name")

  def capacity: Rep[Int] = column[Int]("id", O.PrimaryKey)


  def * : ProvenShape[WarehouceType] = (id, cityId, Warehouce, capacity) <> (WarehouceType.tupled, WarehouceType.unapply) // scalastyle:ignore

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