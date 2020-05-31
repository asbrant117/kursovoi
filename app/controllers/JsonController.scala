package controllers

import db.{UserProfile, UserProfileRepository}
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
      "surname" -> ext.Population,
    )
  }

  implicit val profileRead: Reads[UserProfile] = ((JsPath \ "id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "surname").read[Int]
    ) (UserProfile.apply _)

  def country(id: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(UserProfile(id, "my name", 1234124)))
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
}
