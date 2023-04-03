package hu.elte.inf.wiki.controller

import hu.elte.inf.wiki.model
import hu.elte.inf.wiki.storage.Couchbase

class Article()(implicit couchbase: Couchbase) {
  protected val Articles = new model.Article.Storage()
  // protected val Images = new model.Image.Storage()

  def update(articleID: String, body: String)(userID: String) = {
    val article = Articles.get(articleID).getOrElse(
      throw new NoSuchElementException(s"Article not found by ID [$articleID]!")
    )
    Articles.upsert(article.withNewChange(userID, body))
  }

  def get(articleID: String): Option[model.Article] = Articles.get(articleID)

  def create(body: String)(userID: String): model.Article =
    Articles.upsert(model.Article(userID, body))

  def getAll() = Articles.getAll
}

object Article {}
