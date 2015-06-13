package controllers

import javax.inject.Inject

import dal.PeopleRepository
import models.Person
import models.Person.personFormat
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class PersonController @Inject()(repo: PeopleRepository)(implicit ec: ExecutionContext) extends Controller {

  def list = Action.async { implicit request =>
    repo.list().map(people => Ok(Json.toJson(people)))
  }

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[Person].fold(
      error => {
        Future(BadRequest(Json.obj("status" -> "Error", "message" -> JsError.toJson(error))))
      },
      person => {
        repo.create(person.name).map { personResult =>
          Ok(Json.toJson(personResult))
        }
      }
    )
  }

  def get(id: Int) = Action.async { implicit request =>
    repo.get(id).map(person => Ok(Json.toJson(person)))
  }

  def delete(id: Int) = Action.async { implicit request =>
    repo.delete(id).map(people_affected => Ok(Json.toJson(people_affected)))
  }

  def update(id: Int) = Action.async(parse.json) { implicit request =>
    request.body.validate[Person].fold(
      error => {
        Future(BadRequest(Json.obj("status" -> "Error", "message" -> JsError.toJson(error))))
      },
      person => {
        repo.update(id, person).map { people_affected => Ok(Json.toJson(people_affected))}
      }
    )
  }

}
