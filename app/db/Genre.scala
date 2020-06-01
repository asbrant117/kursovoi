package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class GenreClass(id: Int,
                      genre: String)


class GenreRepository {

  val GenreQuery: TableQuery[GenreTypes] = TableQuery[GenreTypes]

  def insert(user: GenreClass): Future[Int] =
    db.run(GenreQuery += user)

  def get(id: Int): Future[Option[GenreClass]] =
    db.run(
      GenreQuery
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

  def all(): Future[Seq[GenreClass]] =
    db.run(
      GenreQuery
        .result
    )

}

private[db] class GenreTypes(tag: Tag) extends Table[GenreClass](tag, "Genre") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def genre: Rep[String] = column[String]("genre")

  def * : ProvenShape[GenreClass] = (id, genre) <> (GenreClass.tupled, GenreClass.unapply) // scalastyle:ignore

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