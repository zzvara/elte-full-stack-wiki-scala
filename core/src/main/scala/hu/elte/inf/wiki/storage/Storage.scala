package hu.elte.inf.wiki.storage

import hu.elte.inf.wiki.model.Unique

import scala.reflect.ClassTag

abstract class Storage[T <: Unique[T] : ClassTag](collectionName: String)(
  implicit couchbase: Couchbase,
  converter: Converter[T]
) {
  val collection = couchbase.scope.collection(collectionName)

  def getAll = couchbase.cluster.query(
    ???
  )

  def get(ID: String): Option[T] = {
    collection
      .get(ID)
      .map(_.contentAs[T].toOption)
      .toOption
      .flatten
  }

  def insert(item: T): T = {
    collection
      .insert(item.ID, item)
      .get
    item
  }

  def upsert(item: T): T = {
    collection
      .upsert(item.ID, item)
      .get
    item
  }

}
