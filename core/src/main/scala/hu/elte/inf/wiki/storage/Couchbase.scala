package hu.elte.inf.wiki.storage

import com.couchbase.client.scala.{Cluster, ClusterOptions}
import hu.elte.inf.wiki.{Configuration, Logger}

import scala.util.{Failure, Success}

class Couchbase(val cluster: Cluster) extends Logger {
  protected lazy val bucket = cluster.bucket("default")
  lazy val scope = bucket.defaultScope
}

object Couchbase extends Logger {
  def apply()(implicit configuration: Configuration): Couchbase = new Couchbase(createCluster())

  private def createCluster()(implicit configuration: Configuration): Cluster = {
    log.info("Connecting to Couchbase cluster.")
    Cluster.connect(
      connectionString = configuration.get[String]("wiki.couchbase.address"),
      ClusterOptions.create(
        configuration.get[String]("wiki.couchbase.username"),
        configuration.get[String]("wiki.couchbase.password")
      )
    ) match {
      case Failure(exception) =>
        exception.printStackTrace()
        throw exception
      case Success(value) =>
        value
    }
  }

}
