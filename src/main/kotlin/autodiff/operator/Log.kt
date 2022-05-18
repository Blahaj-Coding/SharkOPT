package autodiff.operator

import autodiff.*
import kotlin.math.ln
import kotlin.math.log

class Log(val base: Expression, val expression: Expression) : Expression() {
    private var containedVariables = base.getVariables() + expression.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        value = log(expression.evaluate(variables), base.evaluate(variables))
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        val baseValue = base.value
        val expValue = expression.value
        val baseGradient = -log(expValue, baseValue) / (baseValue * ln(baseValue))
        val expGradient = 1 / (expValue * ln(baseValue))
        base.solveGradient(variables, gradient, path * baseGradient)
        expression.solveGradient(variables, gradient, path * expGradient)
    }

    // Identity log_g(x)f(x) = log(f(x))/log(g(x))
    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        var p1 = forwardAutoDiff(variable, value, degree, expression.forwardAutoDiff(variable, value, degree))
        var p2 = forwardAutoDiff(variable, value, degree, base.forwardAutoDiff(variable, value, degree))
        // Polynomial division - try to find a way to avoid duplicating code already in the Quotient class
        var p = Vector()
        for (k in 0..degree) {
            var ck = p1[k]
            for (i in 0 until k) {
                ck -= p[i] * p2[k - i]
            }
            p.add(ck / p2[0])
        }
        return p
    }

    // Taylor polynomial of ln(f(x))
    fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int, g: Vector): Vector {
        var coef = Vector()
        coef.add(ln(g[0]))
        for (k in 1..degree) {
            var ck = 0.0
            for (i in 1 until k) {
                ck += i * coef[i] * g[k - i]
            }
            coef.add((g[k] - ck / k) / (g[0]))
        }
        return coef
    }

    override fun toString(): String {
        return "log_(${base})[$expression]"
    }
}