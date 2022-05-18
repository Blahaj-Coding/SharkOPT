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

    override fun evaluate(variables: VariableMap): Double {
        value = base.evaluate(variables).pow(exponent.evaluate(variables))
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        var baseJacobian = exponent.value * base.value.pow(exponent.value - 1)
        var expJacobian = this.value * ln(base.value)
        base.solveGradient(variables, gradient, path * baseJacobian)
        exponent.solveGradient(variables, gradient, path * expJacobian)
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        var g = base.forwardAutoDiff(variable, value, degree)
        var a = exponent.value
        var coef = Vector()
        coef.add(g.get(0).pow(a))
        for (k in 1..degree) {
            var ck = 0.0
            for (i in 1..k) {
                ck += ((a + 1) * i / k - 1) * g.get(i) * coef.get(k - i)
            }
            coef.add(ck / g.get(0))
        }
        return coef
    }

    override fun toString(): String {
        return "(${base} ^ (${exponent}))"
    }
}