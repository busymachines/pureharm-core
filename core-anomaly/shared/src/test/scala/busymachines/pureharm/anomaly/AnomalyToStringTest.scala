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

import cats.Show
import munit._

final class AnomalyToStringTest extends CatsEffectSuite {

  private val shower = Show[Throwable]

  test("Show an anomaly w/: no params - no cause") {
    val anom     = InvalidInputAnomaly("this is a test 1")
    val expected =
      """|anomaly:   busymachines.pureharm.anomaly.InvalidInputAnomalyImpl
         |anomalyID: 4
         |message:   this is a test 1
         |""".stripMargin.trim
    assert(clue(shower.show(anom)) == clue(expected))
  }

  test("Show an anomaly w/: params - no cause") {
    val anom     = InvalidInputAnomaly(
      "this is a test 2",
      Anomaly.Parameters("test" -> "first parameter value", "test_seq" -> List("value1", "value2")),
    )
    val expected =
      """|anomaly:   busymachines.pureharm.anomaly.InvalidInputAnomalyImpl
         |anomalyID: 4
         |message:   this is a test 2
         |
         |parameters:
         |  test: first parameter value
         |  test_seq: [value1,value2]
         |""".stripMargin.trim
    assert(clue(shower.show(anom)) == clue(expected))
  }

  test("Show an anomaly w/: params - cause") {
    val anom     = Catastrophe(
      message    = "this is a test 3",
      parameters = Anomaly.Parameters("test" -> "first parameter value", "test_seq" -> List("value1", "value2")),
      causedBy   = new RuntimeException("cause of my catastrophe"),
    )
    val expected =
      """|anomaly:   busymachines.pureharm.anomaly.CatastropheImpl
         |anomalyID: CATA_0
         |message:   this is a test 3
         |
         |causedBy:
         |  java.lang.RuntimeException: cause of my catastrophe
         |
         |parameters:
         |  causedBy: java.lang.RuntimeException: cause of my catastrophe
         |  test: first parameter value
         |  test_seq: [value1,value2]
         |""".stripMargin.trim
    assert(clue(shower.show(anom)) == clue(expected))
  }

  test("Show an anomaly w/: params - cause") {
    val anom     = Catastrophe(
      message    = "this is a test 3",
      parameters = Anomaly.Parameters("test" -> "first parameter value", "test_seq" -> List("value1", "value2")),
      causedBy   = new RuntimeException("cause of my catastrophe"),
    )
    val expected =
      """|anomaly:   busymachines.pureharm.anomaly.CatastropheImpl
         |anomalyID: CATA_0
         |message:   this is a test 3
         |
         |causedBy:
         |  java.lang.RuntimeException: cause of my catastrophe
         |
         |parameters:
         |  causedBy: java.lang.RuntimeException: cause of my catastrophe
         |  test: first parameter value
         |  test_seq: [value1,value2]
         |""".stripMargin.trim
    assert(clue(shower.show(anom)) == clue(expected))
  }

  private case object TestShowID extends AnomalyID { override val name: String = "test_id" }

  test("Show anomalies") {
    val anoms    = Anomalies(
      id       = TestShowID,
      message  = "Here we have multiple anomalies",
      causedBy = new RuntimeException("we also have a cause for all these"),
      InvalidInputAnomaly(
        "first anomaly",
        Anomaly.Parameters("an1.1" -> "an1.1_value", "an1.2" -> List("an1.2_value1", "an1.2_value2")),
      ),
      ConflictAnomaly("second anomaly"),
      ForbiddenAnomaly("third anomaly", Anomaly.Parameters("an3" -> "an3_value")),
    )
    val expected =
      """|anomaly:   busymachines.pureharm.anomaly.AnomaliesImpl
         |anomalyID: test_id
         |message:   Here we have multiple anomalies
         |
         |causedBy:
         |  java.lang.RuntimeException: we also have a cause for all these
         |
         |parameters:
         |  causedBy: java.lang.RuntimeException: we also have a cause for all these
         |
         |anomalies:
         |
         |  [0]:
         |    anomaly:   busymachines.pureharm.anomaly.InvalidInputAnomalyImpl
         |    anomalyID: 4
         |    message:   first anomaly
         |    
         |    parameters:
         |      an1.1: an1.1_value
         |      an1.2: [an1.2_value1,an1.2_value2]
         |
         |  [1]:
         |    anomaly:   busymachines.pureharm.anomaly.ConflictAnomalyImpl
         |    anomalyID: 5
         |    message:   second anomaly
         |
         |  [2]:
         |    anomaly:   busymachines.pureharm.anomaly.ForbiddenFailureImpl
         |    anomalyID: 2
         |    message:   third anomaly
         |    
         |    parameters:
         |      an3: an3_value
         |""".stripMargin.trim
    assert(clue(shower.show(anoms)) == clue(expected))
  }
}
