package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class CountryType(    id: Int,
                       country : String)


class CountryRepository {

  val CountryQuery: TableQuery[CountryTypes] = TableQuery[CountryTypes]

  def insert(user: CountryType): Future[Int] =
    db.run(CountryQuery += user)

  def get(id: Int): Future[Option[CountryType]] =
    db.run(
      CountryQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[CountryType]] =
    db.run(
      CountryQuery
        .result
    )

}

private[db] class CountryTypes(tag: Tag) extends Table[CountryType](tag, "Country") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def country: Rep[String] = column[String]("country")

  def * : ProvenShape[CountryType] = (id, country) <> (CountryType.tupled, CountryType.unapply) // scalastyle:ignore

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