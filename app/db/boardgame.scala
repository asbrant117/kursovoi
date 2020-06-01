package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class Boardgame(id: Int,
                     name: String,
                     genreId: Int,
                     countryId: Int,
                     rating: Int
                    )


class BoardgameRepository {

  val BoardgameQuery: TableQuery[BoardgameTypes] = TableQuery[BoardgameTypes]

  def insert(user: Boardgame): Future[Int] =
    db.run(BoardgameQuery += user)

  def get(id: Int): Future[Option[Boardgame]] =
    db.run(
      BoardgameQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[Boardgame]] =
    db.run(
      BoardgameQuery
        .result
    )

  def update(boardgame: Boardgame): Future[Int] =
      db.run(
        BoardgameQuery
          .filter(_.id === boardgame.id)
          .update(boardgame))

}

private[db] class BoardgameTypes(tag: Tag) extends Table[Boardgame](tag, "Boardgame") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def name: Rep[String] = column[String]("name")

  def genreId: Rep[Int] = column[Int]("genreId")

  def countryId: Rep[Int] = column[Int]("countryId")

  def rating: Rep[Int] = column[Int]("rating")


  def * : ProvenShape[Boardgame] = (id, name, genreId, countryId, rating) <> (Boardgame.tupled, Boardgame.unapply) // scalastyle:ignore

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