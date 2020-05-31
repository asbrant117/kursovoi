package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class SityType(id: Int,
                       genre: String)


class SityRepository {

  val CityQuery: TableQuery[SityTypes] = TableQuery[SityTypes]

  def insert(user: SityType): Future[Int] =
    db.run(CityQuery += user)

  def get(id: Int): Future[Option[SityType]] =
    db.run(
      CityQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[SityType]] =
    db.run(
      CityQuery
        .result
    )

}

private[db] class SityTypes(tag: Tag) extends Table[SityType](tag, "Sity") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def Sity: Rep[String] = column[String]("Sity")

  def * : ProvenShape[SityType] = (id, Sity) <> (SityType.tupled, SityType.unapply) // scalastyle:ignore

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