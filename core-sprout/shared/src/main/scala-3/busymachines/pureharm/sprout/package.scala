
package busymachines.pureharm.sprout

type Sprout[O]                = _root_.sprout.Sprout[O]
type SproutSub[O]             = _root_.sprout.SproutSub[O]

type SproutRefined[O, E]      = _root_.sprout.SproutRefined[O, E]
type SproutRefinedSub[O, E]   = _root_.sprout.SproutRefinedSub[O, E]

type SproutRefinedThrow[O]    = _root_.sprout.SproutRefinedThrow[O]
type SproutRefinedSubThrow[O] = _root_.sprout.SproutRefinedSub[O, Throwable]

type OldType[O, N] = _root_.sprout.OldType[O, N]
val OldType: _root_.sprout.OldType.type = _root_.sprout.OldType

type NewType[O, N] = _root_.sprout.NewType[O, N]
val NewType: _root_.sprout.NewType.type = _root_.sprout.NewType

type RefinedType[O, N, E] = _root_.sprout.RefinedType[O, N, E]
val RefinedType: _root_.sprout.RefinedType.type = _root_.sprout.RefinedType

type RefinedTypeThrow[O, N] = _root_.sprout.RefinedTypeThrow[O, N]
val RefinedTypeThrow: _root_.sprout.RefinedTypeThrow.type = _root_.sprout.RefinedTypeThrow

type SproutShow[O]  = _root_.sprout.SproutShow[O]
type SproutEq[O]    = _root_.sprout.SproutEq[O]
type SproutOrder[O] = _root_.sprout.SproutOrder[O]
