package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class BoardgameType(id: Int,
                         name: String,
                         genreId: Int,
                         countryId: Int,
                         rating: Int
                        )


class Boardgameitory {

  val BoardgameQuery: TableQuery[BoardgameTypes] = TableQuery[BoardgameTypes]

  def insert(user: BoardgameType): Future[Int] =
    db.run(BoardgameQuery += user)

  def get(id: Int): Future[Option[BoardgameType]] =
    db.run(
      BoardgameQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[BoardgameType]] =
    db.run(
      BoardgameQuery
        .result
    )

}

private[db] class BoardgameTypes(tag: Tag) extends Table[BoardgameType](tag, "Genre") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def  name: Rep[String] = column[String]("Name")

  def genreId: Rep[Int] = column[Int]("id")

  def countryId: Rep[Int] = column[Int]("id")

  def rating: Rep[Int] = column[Int]("id")



  def * : ProvenShape[BoardgameType] = (id, name, genreId, countryId,rating) <> (BoardgameType.tupled, BoardgameType.unapply) // scalastyle:ignore

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