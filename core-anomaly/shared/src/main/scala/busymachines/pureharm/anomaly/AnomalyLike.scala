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
    case Some(cause) => super.parameters ++ Anomaly.Parameters("causedBy" -> causeForParameters(cause))
  }

  override def toString: String = cachedToString

  private lazy val cachedToString = this match {
    case ans: Anomalies   => prettyReprMultiple(ans)
    case a:   AnomalyLike => prettyReprSingle(a)
  }

  private def prettyReprMultiple(ans: Anomalies) =
    s"""|anomaly:   ${classNameString(ans)}
        |anomalyID: ${id.name}
        |message:   $message
        |${causeString(ans)}
        |${parametersString(ans)}
        |
        |anomalies:
        |
        |${ans.messages.zipWithIndex.map(printWithIndex).map(indent).mkString("\n\n")}
        |""".stripMargin.trim

  private def printWithIndex(i: (AnomalyBase, Int)): String = {
    val (a, index) = i
    s"""|[$index]:
        |${indent(prettyPrintAnomalyBase(a))}
        |""".stripMargin.trim
  }

  private def prettyPrintAnomalyBase(a: AnomalyBase): String = a match {
    case a: AnomalyLike => prettyReprSingle(a)
    case _ =>
      s"""|anomaly:   ${classNameString(a)}
          |anomalyID: ${id.name}
          |message:   $message
          |${parametersString(a)}
          |""".stripMargin.trim
  }

  private def prettyReprSingle(a: AnomalyLike) =
    s"""|anomaly:   ${classNameString(a)}
        |anomalyID: ${a.id.name}
        |message:   ${a.message}
        |${causeString(a)}
        |${parametersString(a)}
        |""".stripMargin.trim

  private def classNameString(a: AnomalyBase) = a.getClass.getName.stripSuffix("$")

  private def causeString(a: AnomalyLike) = a.causedBy match {
    case None    => ""
    case Some(t) => s"\ncausedBy:\n${indent(t.toString)}\n"
  }

  private def causeForParameters(c: Throwable) = s"${c.getClass.getName.stripSuffix("$")}: ${c.getMessage}"

  private def parametersString(a: AnomalyBase) =
    if (a.parameters.isEmpty) ""
    else s"parameters:\n${indent(a.parameters.map { case (k, v) => s"$k: $v" }.mkString("\n"))}"

  protected def indent(s:         String)      = s.split("\n").map(s => s"  $s").mkString("\n")

}
