package hu.elte.inf.wiki

import hu.elte.inf.wiki.configuration.Factory

class Configuration(silent: Boolean = false)(
  implicit factory: Factory.forConfiguration[Configuration])
 extends configuration.Configuration[Configuration](
   "wiki.conf",
   "wiki.defaults.conf",
   true,
   Some("wiki"),
   silent
 )
   with Serializable {}

object Configuration {

  implicit object configurationFactory extends Factory.forConfiguration[Configuration] {

    override def apply(
      fromFile: String,
      fromEnvironment: Boolean,
      restrictTo: Option[String],
      silent: Boolean
    ): Configuration = new Configuration(silent)

  }

}
