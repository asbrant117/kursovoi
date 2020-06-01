package controllers

import db.{Boardgame, BoardgameRepository, CountryRepository, CountryType, GenreClass, GenreRepository, UserProfile, UserProfileRepository}
import javax.inject._
import play.api.mvc._
import play.api.libs.json.{JsPath, JsValue, Json, Reads, Writes}
import play.api.libs.functional.syntax._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

@Singleton
class JsonController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val repoBG = new BoardgameRepository
  val repoG = new GenreRepository
  val repoC = new CountryRepository

  implicit val boardgameRead: Reads[BoardgameJson] = Json.reads[BoardgameJson]
  implicit val boardgameWrites: Writes[BoardgameJson] = Json.writes[BoardgameJson]

  def boardgame(id: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val gameFuture = repoBG.get(id)
    val game: Boardgame = Await.result(gameFuture, Duration.Inf).get

    val genreFuture: Future[Option[GenreClass]] = repoG.get(game.genreId)
    val genre: Option[GenreClass] = Await.result(genreFuture, Duration.Inf)

    val countryFuture = repoC.get(game.countryId)
    val country = Await.result(countryFuture, Duration.Inf).get.country

    val boardgame = BoardgameJson(id, genre.get.genre, name = game.name, country = country, rating = game.rating)

    Ok(Json.toJson(boardgame))
  }

  def updateBoardgame(): Action[JsValue] = Action(parse.tolerantJson) { implicit request =>
    println(request.body)
    val gameNetwork = request.body.validate[BoardgameJson].get

    val gameFuture = repoBG.get(gameNetwork.id)
    val game: Boardgame = Await.result(gameFuture, Duration.Inf).get

    val newGame = game.copy(name = gameNetwork.name, rating = gameNetwork.rating)
    val futureUpdate = repoBG.update(newGame)
    Await.result(futureUpdate, Duration.Inf)


    if (game.id % 2 == 0) {
      Ok(Json.obj("result" -> "Сохранено"))
    } else {
      Ok(Json.obj("result" -> "Недопустимый формат"))
    }
  }
}

case class BoardgameJson(id: Int, genre: String, name: String, country: String, rating: Int)