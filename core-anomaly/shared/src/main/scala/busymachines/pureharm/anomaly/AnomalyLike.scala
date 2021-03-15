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

package busymachines.pureharm.anomaly

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 11 Jun 2019
  */
abstract class AnomalyLike(
  override val message: String,
  val causedBy:         Option[Throwable],
) extends Throwable(message, causedBy.orNull) with AnomalyBase {

  override def parameters: Anomaly.Parameters = causedBy match {
    case None        => super.parameters
    case Some(cause) => super.parameters ++ Anomaly.Parameters("causedBy" -> cause.toString)
  }
}
