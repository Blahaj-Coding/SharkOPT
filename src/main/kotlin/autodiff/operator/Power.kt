package autodiff.operator

import autodiff.*
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.pow

// TODO: add support for arbitrary non-constant exponents
class Power(val base: Expression, val exponent: Constant): Expression() {
    private var containedVariables = base.getVariables() + exponent.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: Vector): Double {
        value = base.evaluate(variables).pow(exponent.evaluate(variables))
        return value
    }

    // df/du u^v = v * u^(v - 1)
    // df/dv u^v = ln(u) * u^v
    override fun solveGradient(variables: Vector, gradient: Vector, path: Double) {
        var baseGradient = exponent.value * base.value.pow(exponent.value - 1)
        var expGradient = this.value * ln(base.value)
        base.solveGradient(variables, gradient, path * baseGradient)
        exponent.solveGradient(variables, gradient, path * expGradient)
    }

    override fun forwardAutoDiff(variable: Variable, value: Vector, degree: Int): Vector {
        var g = base.forwardAutoDiff(variable, value, degree)
        var a = exponent.value
        var coef = Vector()
        coef.add(g[0].pow(a))
        for (k in 1..degree) {
            var ck = 0.0
            for (i in 1..k) {
                ck += ((a + 1) * i / k - 1) * g[i] * coef[k - i]
            }
            coef.add(ck / g[0])
        }
        return coef
    }

    override fun toString(): String {
        return "(${base} ^ (${exponent}))"
    }
}