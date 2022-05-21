package autodiff.operator

import autodiff.*
import org.ejml.simple.SimpleMatrix
import kotlin.math.ln
import kotlin.math.pow

// TODO: add support for arbitrary non-constant exponents
class Power(val base: Expression, val exponent: Constant): Expression() {
    private var containedVariables = base.getVariables() + exponent.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: SimpleMatrix): Double {
        value = base.evaluate(variables).pow(exponent.evaluate(variables))
        return value
    }

    // df/du u^v = v * u^(v - 1)
    // df/dv u^v = ln(u) * u^v
    override fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double) {
        var baseGradient = exponent.value * base.value.pow(exponent.value - 1)
        var expGradient = this.value * ln(base.value)
        base.solveGradient(variables, gradient, path * baseGradient)
        exponent.solveGradient(variables, gradient, path * expGradient)
    }

    override fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        var g = base.forwardAutoDiff(variable, value, degree)
        var a = exponent.value
        var coef = SimpleMatrix(degree + 1,1)
        coef[0] = g[0].pow(a)
        for (k in 1..degree) {
            var ck = 0.0
            for (i in 1..k) {
                ck += ((a + 1) * i / k - 1) * g[i] * coef[k - i]
            }
            coef[k] = ck / g[0]
        }
        return coef
    }

    override fun toString(): String {
        return "(${base} ^ (${exponent}))"
    }

    constructor(base: Expression, exponent: Double) : this(base, Constant(exponent))
    constructor(base: Double, exponent: Double) : this(Constant(base), Constant(exponent))
    constructor(base: Expression, exponent: Int) : this(base, Constant(exponent))
    constructor(base: Int, exponent: Int) : this(Constant(base), Constant(exponent))
    constructor(base: Double, exponent: Int) : this(Constant(base), Constant(exponent))
    constructor(base: Int, exponent: Double) : this(Constant(base), Constant(exponent))
}