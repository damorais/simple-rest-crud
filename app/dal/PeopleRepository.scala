package dal

import javax.inject.Inject

import models.Person
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.PostgresDriver

import scala.concurrent.{ExecutionContext, Future}

class PeopleRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[PostgresDriver]

  import dbConfig._
  import driver.api._

  private val people = TableQuery[PeopleTable]

  def create(name: String): Future[Person] = db.run {
    (people.map(p => (p.name))
      returning people.map(_.id)
      into ((name, id) => Person(id, name))
      ) += (name)
  }

  def get(id: Long): Future[Person] = db.run {
    people.filter(_.id === id).result.head
  }

  def list(): Future[Seq[Person]] = db.run {
    people.result
  }

  def delete(id: Long): Future[Int] = db.run {
    people.filter(_.id === id).delete
  }

  def update(id: Long, person: Person): Future[Int] = db.run {
    people.filter(_.id === id).map(p => p.name).update(person.name)
  }

  private class PeopleTable(tag: Tag) extends Table[Person](tag, "people") {

    def * = (id, name) <>((Person.apply _).tupled, Person.unapply)

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")
  }

}
