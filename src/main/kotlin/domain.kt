package com.sashashpota

import java.math.BigDecimal
import java.math.BigInteger

enum class H { M, P, T }

data class Input (
    val a: Boolean,
    val b: Boolean,
    val c: Boolean,
    val d: BigDecimal,
    val e: BigInteger,
    val f: BigInteger
)

data class Result(
    val h: H,
    val k: BigDecimal
)