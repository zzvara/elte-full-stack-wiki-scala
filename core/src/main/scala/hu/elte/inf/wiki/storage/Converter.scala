package hu.elte.inf.wiki.storage

import com.couchbase.client.scala.codec.{JsonDeserializer, JsonSerializer}
import org.json4s.{DefaultFormats, Formats, FullTypeHints}
import org.json4s.jackson.Serialization

import java.nio.charset.Charset
import scala.reflect.ClassTag
import scala.util.Try

abstract class Converter[T <: AnyRef : ClassTag : Manifest]
 extends JsonDeserializer[T] with JsonSerializer[T] {

  implicit final protected val serializationFormat: Formats =
    DefaultFormats.withBigDecimal + FullTypeHints(List.empty, "type")

  override def serialize(content: T): Try[Array[Byte]] =
    Try {
      Serialization.write[T](content).getBytes(Converter.charset)
    }

  override def deserialize(bytes: Array[Byte]): Try[T] =
    Try {
      Serialization.read[T](new String(bytes, Converter.charset))
    }

}

object Converter {
  val charset = Charset.forName("UTF-8")
}
