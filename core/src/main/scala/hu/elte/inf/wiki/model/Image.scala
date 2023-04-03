package hu.elte.inf.wiki.model

case class Image(ID: String, bytes: Array[Byte]) extends Unique[Image]

object Image {

}