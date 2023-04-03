package hu.elte.inf.wiki

import org.slf4j.LoggerFactory

trait Logger {

  @transient protected lazy val log = com.typesafe.scalalogging.Logger(LoggerFactory.getLogger(
    this.getClass.getName.stripSuffix("$")
  ))

  implicit class throwableLogger(throwable: Throwable) {

    def logError(action: String): Unit = {
      log.error(
        "Could not [{}] due to exception [{}] with message [{}]!",
        action,
        throwable.getClass.getCanonicalName,
        Option(throwable.getMessage).getOrElse("N/A")
      )
    }

    def logWarn(action: String): Unit = {
      log.warn(
        "Could not [{}] due to exception [{}] with message [{}]!",
        action,
        throwable.getClass.getCanonicalName,
        Option(throwable.getMessage).getOrElse("N/A")
      )
    }

    def logInfo(action: String): Unit = {
      log.info(
        "Could not [{}] due to exception [{}] with message [{}].",
        action,
        throwable.getClass.getCanonicalName,
        Option(throwable.getMessage).getOrElse("N/A")
      )
    }

    def logDebug(action: String): Unit = {
      log.debug(
        "Could not [{}] due to exception [{}] with message [{}].",
        action,
        throwable.getClass.getCanonicalName,
        Option(throwable.getMessage).getOrElse("N/A")
      )
    }

    def logTrace(action: String): Unit = {
      log.trace(
        "Could not [{}] due to exception [{}] with message [{}].",
        action,
        throwable.getClass.getCanonicalName,
        Option(throwable.getMessage).getOrElse("N/A")
      )
    }

  }

}
