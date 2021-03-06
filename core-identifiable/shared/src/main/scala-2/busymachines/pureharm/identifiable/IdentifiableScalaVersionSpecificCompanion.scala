/*
 * Copyright 2019 BusyMachines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package busymachines.pureharm.identifiable

trait IdentifiableScalaVersionSpecificCompanion {
  import IdentifiableScalaVersionSpecificCompanion._
  import shapeless._
  import shapeless.ops.record._

  def apply[T, ID](implicit instance: Identifiable[T, ID]): Identifiable[T, ID] = instance

  implicit def mkIdentifiable[T, ID, IHL <: HList](implicit
    gen:      LabelledGeneric.Aux[T, IHL],
    selector: Selector.Aux[IHL, Witness.`Symbol("id")`.T, ID],
  ): Identifiable[T, ID] = new IdentifiableByID[T, ID] {
    override def id(t: T): ID = selector(gen.to(t))
  }

}

private[pureharm] object IdentifiableScalaVersionSpecificCompanion {
  private val IdFieldName: FieldName = FieldName("id")

  private trait IdentifiableByID[T, ID] extends Identifiable[T, ID] {
    override def fieldName: FieldName = IdFieldName
  }
}
