package hu.elte.inf.wiki

import akka.actor.ActorSystem
import akka.http.scaladsl.Http

class Wiki() extends Logger {

  def start(): Unit = {
    log.info("Starting HTTP server.")
    implicit val system: ActorSystem = ActorSystem()
    val router = new Router()
    Http().newServerAt("localhost", 8080)
      .bind(router.route)
  }

}

object Wiki {
  def main(arguments: Array[String]): Unit = new Wiki().start()
}
