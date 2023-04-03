package hu.elte.inf.wiki.model

import hu.elte.inf.wiki.storage
import hu.elte.inf.wiki.model.Article.Change
import hu.elte.inf.wiki.storage.{Converter, Couchbase}
import org.apache.commons.lang3.RandomStringUtils

case class Article(
  ID: String,
  body: String,
  /**
    * A sequence of image IDs.
    */
  images: Seq[String],
  changes: Seq[Change])
 extends Unique[Article] {
  require(changes.nonEmpty, "At least one change must exist within `changes`!")

  def withNewChange(userID: String, body: String): Article =
    copy(
      body = body,
      changes = changes :+ Change(userID)
    )

}

object Article {

  def apply(userID: String, body: String): Article =
    Article(
      RandomStringUtils.randomAlphabetic(32),
      body,
      Seq.empty,
      Change(userID) :: Nil
    )

  case class Change(timestamp: Long, userID: String) {
    require(timestamp > 0, "Timestamp must be greater then zero!")
  }

  object Change {
    def apply(userID: String): Change = Change(System.currentTimeMillis(), userID)
  }

  implicit object Converter extends Converter[Article]

  class Storage(implicit couchbase: Couchbase) extends storage.Storage[Article]("articles")

}
