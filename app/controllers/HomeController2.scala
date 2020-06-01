package controllers

import db.{UserProfile, UserProfileRepository}
import javax.inject._
import play.api._
import play.api.mvc._
import play.twirl.api.{Html, StringInterpolation}

import scala.collection.immutable
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController2 @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val repo = new UserProfileRepository

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def edit2() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.edit2())
  }

  def edit() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.edit())
  }

  def country(id: Int) = Action { implicit request: Request[AnyContent] =>
    val eventualMaybeUserProfile = repo.get(id)
    val maybeUserProfile: Option[UserProfile] = Await.result(eventualMaybeUserProfile, Duration.Inf)

    if (maybeUserProfile.isDefined) {
      println(maybeUserProfile.get.Name)
    } else {
      println("неверный ввод")
    }
    println(maybeUserProfile)
    Ok(views.html.index())
  }

  def countryUpdate(id: Int, population: Int) = Action { implicit request: Request[AnyContent] =>


    //    Await.result(repo.update(id, population), Duration.Inf)

    val eventualMaybeUserProfile = repo.get(id)
    val maybeUserProfile: Option[UserProfile] = Await.result(eventualMaybeUserProfile, Duration.Inf)

    Ok(maybeUserProfile.toString)
  }

  def all() = Action { implicit request: Request[AnyContent] =>
    val profiles: immutable.Seq[UserProfile] = List(
      UserProfile(1, "Russia"),
      UserProfile(2, "Usa"),
      UserProfile(3, "France"),
      UserProfile(4, "Germany"),
      UserProfile(5, "Italy")
    )

    val htmlProfiles: List[Html] = profiles.map { it =>
      html"""
<tr>
  <th scope="row">${it.id}</th>
  <td>${it.Name}</td>
  <td>${it.Name}</td>
  <td>${it.Name}</td>
  <td>${it.Name}</td>
  <td>${it.Name}</td>
</tr>
"""
    }.toList

    Ok(views.html.list(htmlProfiles))
  }
}
