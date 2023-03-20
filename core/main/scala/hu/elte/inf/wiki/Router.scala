package hu.elte.inf.wiki

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
import akka.http.scaladsl.server.Route

class Router() extends Logger {

  val route: Route =
    pathPrefix("api") {
      pathPrefix("v1") {

        path("ping") {
          get {
            complete(StatusCodes.NoContent)
          }
        } ~
          path("login") {
            post {
              entity(as[Router.Protocol.Request.Login]) {
                login =>
                  complete {
                    userController.login(login.mail, login.password)
                  }
              }
            }
          } ~
          path("register") {
            post {
              ???
            }
          }
      }
    }

}

object Router {
  object Protocol {
    object Request {
      case class Login(mail: String, password: String)
    }
  }
}