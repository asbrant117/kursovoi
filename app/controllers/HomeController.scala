package controllers

import db.{UserProfile, UserProfileRepository}
import javax.inject._
import play.api._
import play.api.mvc._
import play.twirl.api.StringInterpolation

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val repo = new UserProfileRepository

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
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


    Await.result(repo.update(id, population), Duration.Inf)

    val eventualMaybeUserProfile = repo.get(id)
    val maybeUserProfile: Option[UserProfile] = Await.result(eventualMaybeUserProfile, Duration.Inf)

    Ok(maybeUserProfile.toString)
  }

  def all() = Action { implicit request: Request[AnyContent] =>
    val profiles = List(
      UserProfile(1, "Russia", 123123123),
      UserProfile(2, "Usa", 1941513),
      UserProfile(3, "France", 234525),
      UserProfile(4, "Germany", 14452),
      UserProfile(5, "Italy", 59595)
    )

    val htmlProfiles = profiles.map { it =>
      html"""
<tr>
  <th scope="row">${it.id}</th>
  <td>${it.Name}</td>
  <td>${it.Population}</td>
  <td>${it.Name}</td>
  <td>${it.Population}</td>
  <td>${it.Name}</td>
  <td>${it.Population}</td>
  <td>${it.Name}</td>
  <td>${it.Population}</td>
  <td>${it.Name}</td>
  <td>${it.Population}</td>
</tr>
"""
    }

    Ok(views.html.list(htmlProfiles))
  }
}
