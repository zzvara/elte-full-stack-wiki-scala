package hu.elte.inf.wiki.utility

import hu.elte.inf.wiki.Logger
import scala.collection.JavaConverters.asScalaIteratorConverter
import scala.util.matching.Regex

object Utility extends Logger {

  object Resource {

    protected var fileSystems = Map.empty[String, java.nio.file.FileSystem]

    final val JARNameRegex: Regex = """(.+\.jar!).*""".r

    protected def allResources: List[String] = {
      val root = Option(getClass.getResource("/")).map(_.toURI)
        .getOrElse(throw new RuntimeException("Failed to resolve resource root [/]! WTF?"))

      val dirPath = if (root.getScheme == "jar") {
        val fileSystem = getOrCreateFileSystem(root)
        fileSystem.getPath("/")
      } else {
        java.nio.file.Paths.get(root)
      }

      java.nio.file.Files.walk(dirPath).iterator().asScala.toList.map(_.toString)
    }

    def getURL(resource: String): Option[java.net.URL] = Option(getClass.getResource(resource))

    def getURI(resource: String): Option[java.net.URI] = getURL(resource).map(_.toURI)

    def needURL(resource: String): java.net.URL =
      getURL(resource) match {
        case Some(value) => value
        case None =>
          val all = allResources
          log.error(
            "Failed to find resource path [{}]! Logging all [{}] resources.",
            resource,
            all.size
          )
          allResources.zipWithIndex.foreach {
            case (name, index) =>
              log.error("{}. resource: [{}]", index, name)
          }

          throw new RuntimeException(s"Failed to find resource path [$resource]!")
      }

    def needURI(resource: String): java.net.URI =
      getURI(resource) match {
        case Some(value) => value
        case None =>
          val all = allResources
          log.error(
            "Failed to find resource path [{}]! Logging all [{}] resources.",
            resource,
            all.size
          )
          allResources.zipWithIndex.foreach {
            case (name, index) =>
              log.error("{}. resource: [{}]", index, name)
          }

          throw new RuntimeException(s"Failed to find resource path [$resource]!")
      }

    def need[R](resource: String, fJar: java.net.URI => R, fLocal: java.net.URI => R): R = {
      val resourceURI = needURI(resource)
      if (resourceURI.getScheme == "jar") fJar(resourceURI) else fLocal(resourceURI)
    }

    def getOrCreateFileSystem(resourceURI: java.net.URI): java.nio.file.FileSystem = {
      val key = resourceURI.toString match {
        case JARNameRegex(name) => name
        case name => throw new RuntimeException(s"Could not parse jar file name from [$name]!")
      }

      fileSystems.get(key) match {
        case Some(fileSystem) => fileSystem
        case None =>
          fileSystems synchronized {
            if (!fileSystems.contains(key)) {
              val fileSystem = java.nio.file.FileSystems.newFileSystem(
                resourceURI,
                java.util.Collections.emptyMap[String, Any]
              )

              fileSystems = fileSystems + (key -> fileSystem)
            }
          }

          fileSystems(key)
      }
    }

    def path(resource: String): java.nio.file.Path =
      need(
        resource = resource,
        fJar = resourceURI => {
          val fileSystem = getOrCreateFileSystem(resourceURI)
          fileSystem.getPath(resource)
        },
        fLocal = resourceURI => java.nio.file.Paths.get(resourceURI)
      )

    def readAndProcessF[R : scala.util.Using.Releasable, A](
      resource: String,
      fReader: scala.io.BufferedSource => R,
      fTransform: R => A,
      codec: scala.io.Codec = scala.io.Codec.UTF8
    ): A =
      scala.util.Using(
        fReader(scala.io.Source.fromURL(needURL(resource))(codec))
      )(fTransform).get

    def readAndProcess[A](
      resource: String,
      fTransform: scala.io.BufferedSource => A,
      codec: scala.io.Codec = scala.io.Codec.UTF8
    ): A =
      readAndProcessF[scala.io.BufferedSource, A](
        resource,
        identity,
        fTransform,
        codec
      )

    def inputStreamsFromDirectory(
      directory: String,
      fileNameFilter: String => Boolean
    ): List[(String, java.io.InputStream)] = {
      val directoryPath = path(directory)

      java.nio.file.Files.walk(directoryPath, 1).iterator().asScala
        .filter(java.nio.file.Files.isRegularFile(_))
        .filter(path => fileNameFilter(path.toString))
        .map(path => path.getFileName.toString -> path.toUri.toURL.openStream())
        .toList
    }

  }

}
