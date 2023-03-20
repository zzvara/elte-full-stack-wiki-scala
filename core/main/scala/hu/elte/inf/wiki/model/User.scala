package hu.elte.inf.wiki.model

import com.github.t3hnar.bcrypt._
import org.apache.commons.lang3.RandomStringUtils

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

}
