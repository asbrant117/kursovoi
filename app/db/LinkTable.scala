package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class LinkTableType(boardID: Int,
                         werehouseId: Int,
                         number: Int,
                         price: Int)

class LinkTableTypeRepository {

  val LinkTableTypeQuery: TableQuery[LinkTableTypes] = TableQuery[LinkTableTypes]

  def insert(user: LinkTableType): Future[Int] =
    db.run(LinkTableTypeQuery += user)

  def get(id: Int): Future[Option[LinkTableType]] =
    db.run(
      LinkTableTypeQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[LinkTableType]] =
    db.run(
      LinkTableTypeQuery
        .result
    )

}

private[db] class LinkTableTypes(tag: Tag) extends Table[LinkTableType](tag, "Genre") {

  def id: Rep[Int] = column[Int]("id")

  def werehouseId: Rep[Int] = column[Int]("id")

  def number: Rep[Int] = column[Int]("id")

  def price: Rep[Int] = column[Int]("id")

  def * : ProvenShape[LinkTableType] = (id, werehouseId, number,price) <> (LinkTableType.tupled, LinkTableType.unapply) // scalastyle:ignore

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