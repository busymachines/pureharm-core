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

import scala.annotation.implicitNotFound

/** @tparam T
  *   the type
  * @tparam ID
  *   the value by which our value of type ``T`` can be uniquely identified
  * @author
  *   Lorand Szakacs, https://github.com/lorandszakacs
  * @since 04
  *   Apr 2019
  */
@implicitNotFound(
  "If a case class T, has a field called 'id of type ID then an Identifiable[T, ID] will be generated for case class, otherwise, please provide one"
)
trait Identifiable[T, ID] {
  def id(t: T): ID

  def fieldName: FieldName
}

object Identifiable extends IdentifiableScalaVersionSpecificCompanion
