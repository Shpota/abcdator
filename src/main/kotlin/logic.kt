package com.sashashpota

import com.sashashpota.H.*
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP

val basePredicates = listOf<Pair<(Input) -> Boolean, H>>(
    { i: Input -> i.a && i.b && !i.c } to M,
    { i: Input -> i.a && i.b && i.c } to P,
    { i: Input -> !i.a && i.b && i.c } to T
)

val customPredicates2 = listOf<Pair<(Input) -> Boolean, H>>(
    { i: Input -> i.a && i.b && !i.c } to T,
    { i: Input -> i.a && !i.b && i.c } to M
) + basePredicates


val baseFormulas = mapOf<H, (Input) -> BigDecimal>(
    M to { i: Input ->
        (i.d + ((i.d * i.e.toBigDecimal()).divide(BigDecimal("10"))))
            .stripTrailingZeros()
    },
    P to { i: Input ->
        (i.d + ((i.d * (i.e - i.f).toBigDecimal()).divide( BigDecimal("25.5"), 10, HALF_UP)))
            .stripTrailingZeros()
    },
    T to { i: Input ->
        (i.d - ((i.d * i.f.toBigDecimal()).divide(BigDecimal("30"), 10, HALF_UP)))
            .stripTrailingZeros()
    }
)

val customFormulas1 = baseFormulas + (P to { i: Input ->
    (2.toBigDecimal() * i.d + ((i.d * i.e.toBigDecimal()).divide(BigDecimal("100"))))
        .stripTrailingZeros()
})

val customFormulas2 = baseFormulas + (M to { i: Input ->
    (i.f.toBigDecimal() + i.d + (i.d * i.e.toBigDecimal().divide(BigDecimal("100"))))
        .stripTrailingZeros()
})

fun execute(
    input: Input,
    predicates: List<Pair<(Input) -> Boolean, H>>,
    formulas: Map<H, (Input) -> BigDecimal>
): Result {
    for (pair in predicates) {
        if (pair.first(input)) {
            return Result(pair.second, formulas[pair.second]!!(input))
        }
    }
    throw UnsupportedOperationException()
}
