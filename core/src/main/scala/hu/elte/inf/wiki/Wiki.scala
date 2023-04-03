package hu.elte.inf.wiki

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import hu.elte.inf.wiki.storage.Couchbase

class Wiki() extends Logger with Entrypoint {

  def start(): Unit = {
    log.info("Initializing services.")

    implicit val system: ActorSystem = ActorSystem()
    implicit val couchbase: Couchbase = Couchbase()

    val router = new Router()

    log.info("Starting HTTP server.")
    Http().newServerAt("localhost", 8080)
      .bind(router.route)
  }

}

object Wiki {
  def main(arguments: Array[String]): Unit = new Wiki().start()
}

trait Entrypoint {
  implicit val configuration: Configuration = new Configuration()
}
