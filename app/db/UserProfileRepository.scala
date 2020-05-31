package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class UserProfile(id: Int,
                       Name: String,
                       Population: Int)


class UserProfileRepository {

  val userProfileQuery: TableQuery[UserProfiles] = TableQuery[UserProfiles]

  def insert(user: UserProfile): Future[Int] =
    db.run(userProfileQuery += user)

  def get(id: Int): Future[Option[UserProfile]] =
    db.run(
      userProfileQuery
        //        .filter { it => it.Name === "Name" }
        //        .filter { it => it.Name like "%Ru" }
        .filter { it => it.id === id }
        .take(1)
        .result
        .headOption)

//  def getdanger(id: Int): Future[UserProfile] =
//    db.run(
//      userProfileQuery
//        //        .filter { it => it.Name === "Name" }
//        //        .filter { it => it.Name like "%Ru" }
//        .filter { it => it.id === id }
//        .take(1)
//        .result
//        .head)

    def update(id: Int, Population: Int): Future[Int] =
      db.run(
        userProfileQuery
          .filter(_.id === id)
          .map(_.Population)
          .update(Population))

  //  def delete(id: Int): Future[Int] =
  //    db.run(userProfileQuery.filter(_.id === id).delete)

  def all(): Future[Seq[UserProfile]] =
    db.run(
      userProfileQuery
        .result
    )

}

private[db] class UserProfiles(tag: Tag) extends Table[UserProfile](tag, "Country") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def Name: Rep[String] = column[String]("Name")

  def Population: Rep[Int] = column[Int]("Population")

  def * : ProvenShape[UserProfile] = (id, Name, Population) <> (UserProfile.tupled, UserProfile.unapply) // scalastyle:ignore

}
