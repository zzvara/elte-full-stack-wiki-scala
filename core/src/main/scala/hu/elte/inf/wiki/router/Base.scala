package hu.elte.inf.wiki.router

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import hu.elte.inf.wiki.model.Session
import hu.elte.inf.wiki.storage.Couchbase
import org.json4s.{DefaultFormats, Formats, jackson}

trait Base {
  implicit final protected val serializationFormat: Formats = DefaultFormats.withBigDecimal
  implicit final protected val serializationDriver = jackson.Serialization
  implicit protected val couchbase: Couchbase
  protected val Sessions = new Session.Storage()

  def withSession: Directive1[Session] =
    optionalHeaderValueByName("Session").flatMap {
      case Some(sessionID) =>
        Sessions.get(sessionID) match {
          case Some(session) =>
            if (session.alive()) {
              provide(if (session.aboutToExpire()) {
                Sessions.upsert(session.refreshed())
              } else {
                session
              })
            } else {
              reject
            }
          case None => reject
        }
      case None => reject
    }

}
