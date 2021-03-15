
package busymachines.pureharm.identifiable

trait IdentifiableScalaVersionSpecificCompanion {
  import IdentifiableScalaVersionSpecificCompanion._

  def apply[T, ID](implicit instance: Identifiable[T, ID]): Identifiable[T, ID] = instance

  def mkIdentifiable[T, ID](f: T => ID): Identifiable[T, ID] = new IdentifiableByID[T, ID] {
    override def id(t: T): ID = f(t)
  }

}

private[pureharm] object IdentifiableScalaVersionSpecificCompanion {
  private val IdFieldName: FieldName = FieldName("id")

  private trait IdentifiableByID[T, ID] extends Identifiable[T, ID] {
    override def fieldName: FieldName = IdFieldName
  }
}
