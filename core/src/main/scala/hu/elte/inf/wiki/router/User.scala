package hu.elte.inf.wiki.router

import akka.http.scaladsl.server.Directives.{entity, path}
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
import hu.elte.inf.wiki.{controller, Router}
import hu.elte.inf.wiki.storage.Couchbase

trait User extends Base {
  implicit protected val couchbase: Couchbase
  protected val userController = new controller.User()

  def userRoutes =
    path("user") {
      withSession {
        session =>
          get {
            complete {
              userController.get(session.userID)
            }
          } ~
            patch {
              entity(as[Router.Protocol.Request.User]) {
                user =>
                  complete {
                    userController.update(session.userID, user.name)
                  }
              }
            }
      }
    }

}
