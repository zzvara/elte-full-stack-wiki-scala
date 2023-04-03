package hu.elte.inf.wiki

import hu.elte.inf.wiki.model.Article
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class suiteArticle extends AnyFlatSpec with should.Matchers with Logger {
  "An Article" should "be invalid when the `changes` are empty" in {
    log.info("Hello!")
    assertThrows[IllegalArgumentException] {
      new Article("1", "body", Seq.empty, Seq.empty)
    }
  }
  it should "be invalid when the `changes` timestamp is not greater then 0" in {
    assertThrows[IllegalArgumentException] {
      new Article(
        "1",
        "body",
        Seq.empty,
        Article.Change(0, "0") :: Nil
      )
    }
  }
  it should "be valid with non-empty `changes`" in {
    new Article("1", "body", Seq.empty, Seq(Article.Change(1, "1")))
  }
}
