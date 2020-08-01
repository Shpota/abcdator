import com.sashashpota.*
import com.sashashpota.H.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.lang.UnsupportedOperationException
import java.math.BigDecimal
import java.math.BigInteger

internal class LogicTest : ShouldSpec({
    val inp = input("1.5", "2", "5")

    context("test formulas") {
        should("verify formulas: base case") {
            // D + (D * E / 10)
            baseFormulas[M]!!(inp) shouldBe BigDecimal("1.8")
            // D + (D * (E - F) / 25.5)
            baseFormulas[P]!!(inp) shouldBe BigDecimal("1.3235294118")
            // D - (D * F / 30)
            baseFormulas[T]!!(inp) shouldBe BigDecimal("1.25")
        }

        should("verify formulas: case 1") {
            // D + (D * E / 10)
            customFormulas1[M]!!(inp) shouldBe BigDecimal("1.8")
            // 2 * D + (D * E / 100)
            customFormulas1[P]!!(inp) shouldBe BigDecimal("3.03")
            // D - (D * F / 30)
            customFormulas1[T]!!(inp) shouldBe BigDecimal("1.25")
        }

        should("verify formulas: case 2") {
            // F + D + (D * E / 100)
            customFormulas2[M]!!(inp) shouldBe BigDecimal("6.53")
            // D + (D * (E - F) / 25.5)
            customFormulas2[P]!!(inp) shouldBe BigDecimal("1.3235294118")
            // D - (D * F / 30)
            customFormulas2[T]!!(inp) shouldBe BigDecimal("1.25")
        }
    }

    context("test predicates") {
        should("verify predicates: base case") {
            basePredicates[0].first(input(true, true, false)) shouldBe true
            basePredicates[0].second shouldBe M
            basePredicates[1].first(input(true, true, true)) shouldBe true
            basePredicates[1].second shouldBe P
            basePredicates[2].first(input(false, true, true)) shouldBe true
            basePredicates[2].second shouldBe T
        }

        should("verify predicates: custom case 2") {
            customPredicates2[0].first(input(true, true, false)) shouldBe true
            customPredicates2[0].second shouldBe T
            customPredicates2[1].first(input(true, false, true)) shouldBe true
            customPredicates2[1].second shouldBe M
            customPredicates2[3].first(input(true, true, true)) shouldBe true
            customPredicates2[3].second shouldBe P
            customPredicates2[4].first(input(false, true, true)) shouldBe true
            customPredicates2[4].second shouldBe T
        }
    }

    context("test execute") {
        should("produce result") {
            execute(
                input(true, true, false),
                customPredicates2,
                customFormulas2
            ) shouldBe Result(T, BigDecimal("1.25"))
        }

        shouldThrow<UnsupportedOperationException> {
            execute(
                input(true, false, true),
                basePredicates,
                baseFormulas
            )
        }
    }
})

fun input(d: String, e: String, f: String) = Input(
    a = true,
    b = true,
    c = true,
    d = BigDecimal(d),
    e = BigInteger(e),
    f = BigInteger(f)
)

fun input(a: Boolean, b: Boolean, c: Boolean) = Input(
    a = a,
    b = b,
    c = c,
    d = BigDecimal("1.5"),
    e = BigInteger("2"),
    f = BigInteger("5")
)