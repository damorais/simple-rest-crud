package models

import play.api.libs.json.Json

case class Person(id:Long, name:String)

object Person {
  implicit val personFormat = Json.format[Person]
}
