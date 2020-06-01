package db

import slick.jdbc.SQLServerProfile.api._
import slick.lifted.ProvenShape
import DB.db
import scala.concurrent.Future

case class UserProfile(id: Int,
                       Name: String,
                      )


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


//  def update(id: Int, Population: Int): Future[Int] =
//    db.run(
//      userProfileQuery
//        .filter(_.id === id)
//        .map(_.Population)
//        .update(Population))


  def all(): Future[Seq[UserProfile]] =
    db.run(
      userProfileQuery
        .result
    )

}

private[db] class UserProfiles(tag: Tag) extends Table[UserProfile](tag, "Country") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

  def Name: Rep[String] = column[String]("Name")

  def * : ProvenShape[UserProfile] = (id, Name) <> (UserProfile.tupled, UserProfile.unapply) // scalastyle:ignore

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