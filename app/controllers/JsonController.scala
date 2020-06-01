package controllers

import db.{CountryType, UserProfile, UserProfileRepository}
import javax.inject._
import play.api.mvc._
import play.api.libs.json.{JsPath, JsValue, Json, Reads, Writes}
import play.api.libs.functional.syntax._

@Singleton
class JsonController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val repo = new UserProfileRepository

  implicit val profileWrite: Writes[UserProfile] = (ext: UserProfile) => {
    Json.obj(
      "id" -> ext.id,
      "name" -> ext.Name,
    )
  }

  implicit val profileRead: Reads[UserProfile] = ((JsPath \ "id").read[Int] and
    (JsPath \ "name").read[String]
    ) (UserProfile.apply _)

  implicit val countryWrite: Writes[CountryType] = (ext: CountryType) => {
    Json.obj(
      "id" -> ext.id,
      "country" -> ext.country
    )
  }

  implicit val countryRead: Reads[CountryType] = ((JsPath \ "id").read[Int] and
    (JsPath \ "country").read[String]
    ) (CountryType.apply _)

  implicit val boardgameRead: Reads[BoardgameJson] = Json.reads[BoardgameJson]
  implicit val boardgameWrites: Writes[BoardgameJson] = Json.writes[BoardgameJson]

  def country(id: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val profile = UserProfile(id, "my name")
    Ok(Json.toJson(profile))
  }

  def boardgame(id: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val boardgame = BoardgameJson(id, "genre1", "name1", "country1", 3)
    Ok(Json.toJson(boardgame))
  }

  def updateCountry(): Action[JsValue] = Action(parse.tolerantJson) { implicit request =>
    println(request.body)
    val profile = request.body.validate[UserProfile].get
    if (profile.id % 2 == 0) {
      Ok(Json.obj("result" -> "Сохранено"))
    } else {
      Ok(Json.obj("result" -> "Недопустимый формат"))
    }
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