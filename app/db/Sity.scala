package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class CityType(id: Int,
                       genre: String)


class GenreRepository {

  val CityQuery: TableQuery[CityTypes] = TableQuery[CityTypes]

  def insert(user: CityType): Future[Int] =
    db.run(CityQuery += user)

  def get(id: Int): Future[Option[CityType]] =
    db.run(
      CityQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[CityType]] =
    db.run(
      CityQuery
        .result
    )

}

private[db] class CityTypes(tag: Tag) extends Table[CityType](tag, "City") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def City: Rep[String] = column[String]("Name")

  def * : ProvenShape[CityType] = (id, City) <> (CityType.tupled, CityType.unapply) // scalastyle:ignore

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