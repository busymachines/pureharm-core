# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

# unreleased

# 0.1.0

Split out from [pureharm](https://github.com/busymachines/pureharm) as of version `0.0.7`.

Newly cross published for both Scala 2.13, and 3.0.0-RC1 on JVM and JS runtimes.

- add instance for Show[Throwable] to `busymachines.pureharm.PureharmCoreImplicits`

`busymachines.pureharm.anomaly`:
- add `AnomalyLike` super-type that is inherited by `Anomaly`, `Catastrophe`, `Anomalies`, this allows
  for easy non-anomaly catch-all that allows rethrowing.

:warning: Source incompatible changes :warning::
- delete deprecated `PhantomType` and `SafePhantomType`
- delete package `busymachines.pureharm.phantom`, move to `busymachines.pureharm.sprout`

Dependencies:
- [sprout](https://github.com/lorandszakacs/sprout) `0.0.1`
- [cats](https://github.com/typelevel/cats) `2.4.2`
- [shapeless](https://github.com/milessabin/shapeless) `2.3.3` — for Scala 2
