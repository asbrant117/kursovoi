package controllers

import db.{BoardgameRepository, CountryType, GenreRepository, UserProfile, UserProfileRepository}
import javax.inject._
import play.api.mvc._
import play.api.libs.json.{JsPath, JsValue, Json, Reads, Writes}
import play.api.libs.functional.syntax._

@Singleton
class JsonController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val repoBG = new BoardgameRepository
  val repoG = new GenreRepository


  implicit val boardgameRead: Reads[BoardgameJson] = Json.reads[BoardgameJson]
  implicit val boardgameWrites: Writes[BoardgameJson] = Json.writes[BoardgameJson]

  def boardgame(id: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val boardgame = BoardgameJson(id, "genre1", "name1", "country1", 3)
    Ok(Json.toJson(boardgame))
  }

  def updateBoardgame(): Action[JsValue] = Action(parse.tolerantJson) { implicit request =>
    println(request.body)
    val game = request.body.validate[BoardgameJson].get
    if (game.id % 2 == 0) {
      Ok(Json.obj("result" -> "Сохранено"))
    } else {
      Ok(Json.obj("result" -> "Недопустимый формат"))
    }
  }
}

case class BoardgameJson(id: Int, genre: String, name: String, country: String, rating: Int)