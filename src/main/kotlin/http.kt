package com.sashashpota

import org.http4k.contract.ContractRoute
import org.http4k.contract.contract
import org.http4k.contract.meta
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNPROCESSABLE_ENTITY
import org.http4k.lens.*
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.lang.UnsupportedOperationException
import java.math.BigDecimal

fun main() {
    val app: HttpHandler = routes(contract {
        routes += setOf(
            route("/calculate/base", basePredicates, baseFormulas),
            route("/calculate/custom-1", basePredicates, customFormulas1),
            route("/calculate/custom-2", customPredicates2, customFormulas2)
        )
    })
    app.asServer(SunHttp(8000)).start()
}

fun route(
    path: String,
    predicates: List<Pair<(Input) -> Boolean, H>>,
    formulas: Map<H, (Input) -> BigDecimal>
): ContractRoute {
    val aQuery = Query.boolean().required("A")
    val bQuery = Query.boolean().required("B")
    val cQuery = Query.boolean().required("C")
    val dQuery = Query.bigDecimal().required("D")
    val eQuery = Query.bigInteger().required("E")
    val fQuery = Query.bigInteger().required("F")
    val spec = path meta {
        queries += setOf(aQuery, bQuery, cQuery, dQuery, eQuery, fQuery)
    } bindContract GET
    val calc: HttpHandler = { request ->
        val a = aQuery(request)
        val b = bQuery(request)
        val c = cQuery(request)
        val d = dQuery(request)
        val e = eQuery(request)
        val f = fQuery(request)
        try {
            val (h, k) = execute(
                Input(a, b, c, d, e, f), predicates, formulas
            )
            Response(OK).body("H = $h, K = $k")
        } catch (e: UnsupportedOperationException) {
            Response(UNPROCESSABLE_ENTITY)
                .body("Not supported combination")
        }
    }
    return spec to calc
}