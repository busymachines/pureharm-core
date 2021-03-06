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

//=============================================================================
//============================== build details ================================
//=============================================================================

Global / onChangedBuildSource := ReloadOnSourceChanges

val Scala213 = "2.13.6"
val Scala3   = "3.0.1"

//=============================================================================
//============================ publishing details =============================
//=============================================================================

//see: https://github.com/xerial/sbt-sonatype#buildsbt
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"

ThisBuild / baseVersion      := "0.3"
ThisBuild / organization     := "com.busymachines"
ThisBuild / organizationName := "BusyMachines"
ThisBuild / homepage         := Option(url("https://github.com/busymachines/pureharm-core"))

ThisBuild / scmInfo := Option(
  ScmInfo(
    browseUrl  = url("https://github.com/busymachines/pureharm-core"),
    connection = "git@github.com:busymachines/pureharm-core.git",
  )
)

/** I want my email. So I put this here. To reduce a few lines of code, the sbt-spiewak plugin generates this (except
  * email) from these two settings:
  * {{{
  * ThisBuild / publishFullName   := "Loránd Szakács"
  * ThisBuild / publishGithubUser := "lorandszakacs"
  * }}}
  */
ThisBuild / developers := List(
  Developer(
    id    = "lorandszakacs",
    name  = "Loránd Szakács",
    email = "lorand.szakacs@protonmail.com",
    url   = new java.net.URL("https://github.com/lorandszakacs"),
  )
)

ThisBuild / startYear  := Some(2019)
ThisBuild / licenses   := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

//until we get to 1.0.0, we keep strictSemVer false
ThisBuild / strictSemVer              := false
ThisBuild / spiewakCiReleaseSnapshots := false
ThisBuild / spiewakMainBranches       := List("main")
ThisBuild / Test / publishArtifact    := false

ThisBuild / scalaVersion       := Scala213
ThisBuild / crossScalaVersions := List(Scala213, Scala3)

//required for binary compat checks
ThisBuild / versionIntroduced := Map(
  Scala213 -> "0.1.0",
  Scala3   -> "0.3.0",
)

//=============================================================================
//================================ Dependencies ===============================
//=============================================================================
// format: off
val shapeless2V         = "2.3.7"    //https://github.com/milessabin/shapeless/releases
val catsV               = "2.6.1"    //https://github.com/typelevel/cats/releases
val sproutV             = "0.0.5"    //https://github.com/lorandszakacs/sprout/releases
val munitCatsEffect     = "1.0.5"    //https://github.com/typelevel/munit-cats-effect/releases
// format: on
//=============================================================================
//============================== Project details ==============================
//=============================================================================

lazy val root = project
  .in(file("."))
  .aggregate(
    coreJVM,
    coreJS,
    `core-anomalyJVM`,
    `core-anomalyJS`,
    `core-identifiableJVM`,
    `core-identifiableJS`,
    `core-sproutJVM`,
    `core-sproutJS`,
  )
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(SonatypeCiReleasePlugin)
  .settings(commonSettings)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .in(file("./core"))
  .settings(commonSettings)
  .jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .settings(
    name := "pureharm-core",
    libraryDependencies ++= Seq(),
  )
  .dependsOn(
    `core-anomaly`,
    `core-identifiable`,
    `core-sprout`,
  )

lazy val coreJVM = core.jvm.settings(
  javaOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val coreJS = core.js

//=============================================================================

lazy val `core-anomaly` = crossProject(JVMPlatform, JSPlatform)
  .in(file("./core-anomaly"))
  .settings(commonSettings)
  .jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .settings(
    name := "pureharm-core-anomaly",
    libraryDependencies ++= Seq(
      // format: off
      "org.typelevel"   %%% "cats-core"               % catsV                    withSources(),
      "org.typelevel"   %%% "munit-cats-effect-2"     % munitCatsEffect   % Test withSources(),
      // format: on
    ),
  )

lazy val `core-anomalyJVM` = `core-anomaly`.jvm.settings(
  javaOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val `core-anomalyJS` = `core-anomaly`.js

//=============================================================================

lazy val `core-sprout` = crossProject(JVMPlatform, JSPlatform)
  .in(file("./core-sprout"))
  .settings(commonSettings)
  .jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .settings(
    name := "pureharm-core-sprout",
    libraryDependencies ++= Seq(
      // format: off
      "org.typelevel"           %%% "cats-core"       % catsV           withSources(),
      "com.lorandszakacs"       %%% "sprout"          % sproutV         withSources(),
      // format: on
    ),
  )

lazy val `core-sproutJVM` = `core-sprout`.jvm.settings(
  javaOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val `core-sproutJS` = `core-sprout`.js

//=============================================================================

lazy val `core-identifiable` = crossProject(JVMPlatform, JSPlatform)
  .in(file("./core-identifiable"))
  .settings(commonSettings)
  .jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .settings(
    name := "pureharm-core-identifiable",
    libraryDependencies ++= Seq(
    ) ++ (if (isDotty.value) {
            Seq(
            )
          }
          else {
            Seq(
              "com.chuusai" %%% "shapeless" % shapeless2V withSources ()
            )
          }),
  )
  .dependsOn(
    `core-sprout`
  )

lazy val `core-identifiableJVM` = `core-identifiable`.jvm.settings(
  javaOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val `core-identifiableJS` = `core-identifiable`.js

//=============================================================================
//================================= Settings ==================================
//=============================================================================

lazy val commonSettings = Seq()
