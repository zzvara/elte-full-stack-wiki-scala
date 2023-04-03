package hu.elte.inf.wiki.model

import com.couchbase.client.scala.query.{QueryOptions, QueryParameters}
import com.github.t3hnar.bcrypt._
import org.apache.commons.lang3.RandomStringUtils
import hu.elte.inf.wiki.storage
import hu.elte.inf.wiki.storage.{Converter, Couchbase}

case class User(
  ID: String,
  name: String,
  mail: String,
  password: String,
  created: Long)
 extends Unique[User] {
  def withName(name: String): User = copy(name = name)
}

object User {

  def apply(name: String, mail: String, password: String): User =
    User(
      RandomStringUtils.randomAlphabetic(32),
      name = name,
      mail = mail,
      password = password.boundedBcrypt,
      created = System.currentTimeMillis()
    )

  implicit object Converter extends Converter[User]

  class Storage(implicit couchbase: Couchbase) extends storage.Storage[User]("users") {
    def getByMail(mail: String): Option[User] = {
      couchbase.cluster.query(
        "SELECT * FROM `default`.`_default`.`users` AS u " +
        "WHERE u.mail = $mail",
        QueryOptions(
          readonly = Some(true),
          parameters = Some(QueryParameters.Named("$mail" -> mail))
        )
      ).get.rowsAs[User].get
    }.ensuring(_.size < 2).headOption
  }
}
