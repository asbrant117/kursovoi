package controllers

import db.{UserProfile, UserProfileRepository}
import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val repo = new UserProfileRepository

  def index() = Action { implicit request: Request[AnyContent] =>

    Ok(views.html.index())
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
}
