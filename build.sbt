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

addCommandAlias("github-gen", "githubWorkflowGenerate")
addCommandAlias("github-check", "githubWorkflowCheck")
Global / onChangedBuildSource := ReloadOnSourceChanges

val Scala213  = "2.13.5"
val Scala3RC1 = "3.0.0-RC1"

//=============================================================================
//============================ publishing details =============================
//=============================================================================

ThisBuild / baseVersion  := "0.1.0"
ThisBuild / organization := "com.busymachines"
ThisBuild / organizationName := "BusyMachines"
ThisBuild / homepage     := Option(url("https://github.com/busymachines/pureharm-core"))

ThisBuild / scmInfo := Option(
  ScmInfo(
    browseUrl  = url("https://github.com/busymachines/pureharm-core"),
    connection = "git@github.com:busymachines/pureharm-core.git",
  )
)

/** I want my email. So I put this here. To reduce a few lines of code,
  * the sbt-spiewak plugin generates this (except email) from these two settings:
  * {{{
  * ThisBuild / publishFullName   := "Loránd Szakács"
  * ThisBuild / publishGithubUser := "lorandszakacs"
  * }}}
  */
ThisBuild / publishFullName := "Loránd Szakács"
ThisBuild / developers := List(
  Developer(
    id    = "lorandszakacs",
    name  = "Loránd Szakács",
    email = "lorand.szakacs@protonmail.com",
    url   = new java.net.URL("https://github.com/lorandszakacs"),
  )
)

ThisBuild / startYear := Some(2019)
ThisBuild / licenses   := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

//until we get to 1.0.0, we keep strictSemVer false
ThisBuild / strictSemVer              := false
ThisBuild / spiewakCiReleaseSnapshots := false
ThisBuild / spiewakMainBranches       := List("main")
ThisBuild / Test / publishArtifact    := false

ThisBuild / scalaVersion       := Scala213
ThisBuild / crossScalaVersions := List(Scala213, Scala3RC1)

//required for binary compat checks
ThisBuild / versionIntroduced := Map(
  Scala213  -> "0.1.0",
  Scala3RC1 -> "0.1.0",
)

//=============================================================================
//================================ Dependencies ===============================
//=============================================================================

val shapeless2V = "2.3.3"    //https://github.com/milessabin/shapeless/releases
val catsV       = "2.4.2"    //https://github.com/typelevel/cats/releases
val sproutV     = "0.0.1"    //https://github.com/lorandszakacs/sprout/releases
//=============================================================================
//============================== Project details ==============================
//=============================================================================

lazy val root = project
  .in(file("."))
  .aggregate(
    coreJVM,
    `core-anomalyJVM`,
    `core-identifiableJVM`,
    `core-sproutJVM`,
  )
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(SonatypeCiReleasePlugin)
  .settings(commonSettings)

lazy val core = crossProject(JVMPlatform)
  .in(file("./core"))
  .settings(commonSettings)
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

//=============================================================================

lazy val `core-anomaly` = crossProject(JVMPlatform)
  .in(file("./core-anomaly"))
  .settings(commonSettings)
  .settings(
    name := "pureharm-core-anomaly",
    libraryDependencies ++= Seq(),
  )

lazy val `core-anomalyJVM` = `core-anomaly`.jvm.settings(
  javaOptions ++= Seq("-source", "1.8", "-target", "1.8")
)
//=============================================================================

lazy val `core-sprout` = crossProject(JVMPlatform)
  .in(file("./core-sprout"))
  .settings(commonSettings)
  .settings(
    name := "pureharm-core-sprout",
    libraryDependencies ++= Seq(
      "org.typelevel"     %%% "cats-core" % catsV   withSources (),
      "com.lorandszakacs" %%% "sprout"    % sproutV withSources (),
    ),
  )

lazy val `core-sproutJVM` = `core-sprout`.jvm.settings(
  javaOptions ++= Seq("-source", "1.8", "-target", "1.8")
)
//=============================================================================

lazy val `core-identifiable` = crossProject(JVMPlatform)
  .in(file("./core-identifiable"))
  .settings(commonSettings)
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

//=============================================================================

//=============================================================================
//================================= Settings ==================================
//=============================================================================

lazy val commonSettings = Seq(
  Compile / unmanagedSourceDirectories ++= {
    val major = if (isDotty.value) "-3" else "-2"
    List(CrossType.Pure, CrossType.Full).flatMap(
      _.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + major))
    )
  },
  Test / unmanagedSourceDirectories ++= {
    val major = if (isDotty.value) "-3" else "-2"
    List(CrossType.Pure, CrossType.Full).flatMap(
      _.sharedSrcDir(baseDirectory.value, "test").toList.map(f => file(f.getPath + major))
    )
  },
)
