package hu.elte.inf.wiki.model

import org.apache.commons.lang3.RandomStringUtils

import scala.concurrent.duration.DurationInt

case class Session(ID: String, userID: String, expiry: Long) extends Unique[Session] {
  def alive(): Boolean = System.currentTimeMillis() < expiry
  def aboutToExpire(): Boolean =
    if (alive()) {
      val remaining = expiry - System.currentTimeMillis()
      remaining < 1.hour.toMillis
    } else {
      false
    }

  def refreshed(): Session = copy(expiry = System.currentTimeMillis() + expiry)
}

object Session {
  def apply(userID: String): Session =
    Session(
      RandomStringUtils.randomAlphanumeric(32),
      userID,
      System.currentTimeMillis() + expiry
    )

  protected val expiry = 24.hour.toMillis
}