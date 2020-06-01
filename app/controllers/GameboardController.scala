package controllers

import db.{Boardgame, BoardgameRepository}
import db.{GenreClass, GenreRepository}
import javax.inject._
import play.api.mvc._
import play.twirl.api.{Html, StringInterpolation}

import scala.collection.immutable
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class GameboardController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val repoBG = new BoardgameRepository
  val repoG = new GenreRepository

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def edit2() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.edit2())
  }

  def edit() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.edit())
  }

  def Gameboard(id: Int) = Action { implicit request: Request[AnyContent] =>
    val eventualMaybeUserProfile = repoBG.get(id)
    val maybeUserProfile: Option[Boardgame] = Await.result(eventualMaybeUserProfile, Duration.Inf)

    if (maybeUserProfile.isDefined) {
      println(maybeUserProfile.get.id)
    } else {
      println("неверный ввод")
    }
    println(maybeUserProfile)
    Ok(views.html.index())
  }

  def all() = Action { implicit request: Request[AnyContent] =>

    val gamesFuture = repoBG.all()
    val games: Seq[Boardgame] = Await.result(gamesFuture, Duration.Inf)
 

    val htmlProfiles: List[Html] = games.map { it =>

      val genreFuture: Future[Option[GenreClass]] = repoG.get(it.genreId)
      val genre: Option[GenreClass] = Await.result(genreFuture, Duration.Inf)


      html"""
<tr>
  <th scope="row">${it.id}</th>
  <td>${it.name}</td>
  <td>${genre.get.genre}</td>
  <td>${it.countryId}</td>
  <td>${it.rating}</td>
</tr>
"""
    }.toList

    Ok(views.html.list(htmlProfiles))
  }
}