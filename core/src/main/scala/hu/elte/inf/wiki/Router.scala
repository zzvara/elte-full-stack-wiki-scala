package hu.elte.inf.wiki

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
import hu.elte.inf.wiki.storage.Couchbase

class Router(implicit protected val couchbase: Couchbase)
 extends router.User with router.Article with Logger {

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
              entity(as[Router.Protocol.Request.Register]) {
                register =>
                  complete {
                    userController.register(register.mail, register.password, register.name)
                  }
              }
            }
          } ~
          userRoutes ~
          articleRoutes
      }
    }

}

object Router {

  object Protocol {

    object Request {
      case class Login(mail: String, password: String)
      case class Register(mail: String, password: String, name: String)
      case class User(name: String)
      case class Article(body: String)
    }

  }

}
