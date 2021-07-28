# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

# unreleased

# 0.3.0

This is the first release for stable Scala 3!

Scala versions:
- add `2.13.6` w/ `-Xsource:3` compiler flag.
- add support for `3.0.1`
- drop `3.0.0-RC1`, `3.0.0-RC2`

Dependency upgrades:
- [cats](https://github.com/typelevel/cats) `2.6.2`
- [sprout](https://github.com/lorandszakacs/sprout) `0.0.5`

### internals
- bump scalafmt to `3.0.0-RC6` — from `2.7.5`
- bump sbt to `1.5.5`
- bump sbt-spiewak to `0.21.0`
- bump sbt-scalafmt to `2.4.3`
- bump sbt-scalajs-crossproject to `1.1.0`
- bump sbt-scalajs to `1.6.0`

# 0.2.0
- add alias for `sprout.Burry` in `PureharmSproutAliases`
- remove `Show[New]` instances for any `OldType[Old, New]`, it seriously tripped up type inference
- [pureharm-core-anomaly] now depends on cats. Move `Show[Throwable]` instance to anomaly package. The change is source compatible if you mixed in AnomalyImplicits.
- add pretty printed `.toString` implementation for all Anomalies.

New Scala versions:
- 3.0.0-RC2

Dependency upgrades:
- [cats](https://github.com/typelevel/cats) `2.5.0`
- [sprout](https://github.com/lorandszakacs/sprout) `0.0.2`

# 0.1.0

Split out from [pureharm](https://github.com/busymachines/pureharm) as of version `0.0.7`.

Newly cross published for both Scala 2.13, and 3.0.0-RC1 on JVM and JS runtimes.

- add instance for Show[Throwable] to `busymachines.pureharm.PureharmCoreImplicits`

`busymachines.pureharm.anomaly`:
- add `AnomalyLike` super-type that is inherited by `Anomaly`, `Catastrophe`, `Anomalies`, this allows
  for easy non-anomaly catch-all that allows rethrowing.

:warning: Source incompatible changes :warning::
- delete deprecated `PhantomType` and `SafePhantomType`
- delete package `busymachines.pureharm.phantom`, move sprouts to `busymachines.pureharm.sprout`

Dependencies:
- [sprout](https://github.com/lorandszakacs/sprout) `0.0.1`
- [cats](https://github.com/typelevel/cats) `2.4.2`
- [shapeless](https://github.com/milessabin/shapeless) `2.3.3` — for Scala 2
