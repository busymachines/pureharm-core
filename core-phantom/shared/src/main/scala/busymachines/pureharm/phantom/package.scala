/*
 * Copyright 2021 Loránd Szakács
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

package busymachines.pureharm

package object phantom {
  type Sprout[O]                = sprout.Sprout[O]
  type SproutSub[O]             = sprout.SproutSub[O]
  type SproutRefined[O, E]      = sprout.SproutRefined[O, E]
  type SproutRefinedSub[O, E]   = sprout.SproutRefinedSub[O, E]
  type SproutRefinedThrow[O]    = sprout.SproutRefinedThrow[O]
  type SproutRefinedSubThrow[O] = sprout.SproutRefinedSub[O, Throwable]

  type OldType[O, N] = sprout.OldType[O, N]
  val OldType: sprout.OldType.type = sprout.OldType
  type NewType[O, N] = sprout.NewType[O, N]
  val NewType: sprout.NewType.type = sprout.NewType
  type RefinedType[O, N, E] = sprout.RefinedType[O, N, E]
  val RefinedType: sprout.RefinedType.type = sprout.RefinedType
  type RefinedTypeThrow[O, N] = sprout.RefinedTypeThrow[O, N]
  val RefinedTypeThrow: sprout.RefinedTypeThrow.type = sprout.RefinedTypeThrow

  type SproutShow[O]  = sprout.SproutShow[O]
  type SproutEq[O]    = sprout.SproutEq[O]
  type SproutOrder[O] = sprout.SproutOrder[O]
}
