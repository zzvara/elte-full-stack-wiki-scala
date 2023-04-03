package hu.elte.inf.wiki.controller

import com.github.t3hnar.bcrypt._
import hu.elte.inf.wiki.model
import hu.elte.inf.wiki.model.Session
import hu.elte.inf.wiki.storage.Couchbase

import scala.util.chaining.scalaUtilChainingOps

class User()(implicit couchbase: Couchbase) {
  protected val Users = new model.User.Storage()
  protected val Sessions = new model.Session.Storage()

  def update(userID: String, name: String): model.User =
    Users.upsert {
      Users.get(userID).getOrElse {
        throw new IllegalArgumentException(s"User with ID [$userID] not found in the database!")
      }.withName(name)
    }

  def get(userID: String): Option[model.User] = Users.get(userID)

  def register(mail: String, password: String, name: String): User.Login = {
    require(password.length > 6, "Password is too weak!")
    require(name.nonEmpty, "Name can not empty!")
    require(Users.getByMail(mail).isEmpty, s"User with mail [$mail] already exists!")

    Users
      .insert(model.User(name, mail, password))
      .pipe(login)
  }

  def login(mail: String, password: String): User.Login =
    Users
      .getByMail(mail)
      .getOrElse(
        throw new IllegalArgumentException(s"User with mail [$mail] was not found in the database!")
      )
      .tap(_.password.isBcryptedBounded(password).ensuring(
        _ == true,
        "User did not provide a matching password!"
      ))
      .pipe(login)

  protected def login(user: model.User): User.Login = {
    val session = Sessions.upsert(Session(user.ID))
    User.Login(session.ID, user.ID, user.mail, user.name)
  }

}

object User {
  case class Login(sessionID: String, userID: String, userMail: String, userName: String)
}
