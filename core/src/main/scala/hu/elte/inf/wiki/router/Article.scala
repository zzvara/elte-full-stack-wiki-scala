package hu.elte.inf.wiki.router

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
import hu.elte.inf.wiki.{controller, Router}
import hu.elte.inf.wiki.storage.Couchbase

trait Article extends Base {
  implicit protected val couchbase: Couchbase
  protected val articleController = new controller.Article()

  def articleRoutes =
    pathPrefix("article") {
      pathEnd {
        get {
          complete {
            articleController.getAll()
          }
        } ~
          withSession {
            session =>
              post {
                entity(as[Router.Protocol.Request.Article]) {
                  article =>
                    complete {
                      articleController.create(article.body)(session.userID)
                    }
                }
              }
          }
      } ~
        path(Segment) {
          articleID =>
            get {
              complete {
                articleController.get(articleID)
              }
            } ~
              withSession {
                session =>
                  patch {
                    entity(as[Router.Protocol.Request.Article]) {
                      article =>
                        complete {
                          articleController.update(articleID, article.body)(session.ID)
                        }
                    }
                  }
              } ~
              pathPrefix("image") {
                pathEnd {
                  ???
                }
              }
        }
    }

}
