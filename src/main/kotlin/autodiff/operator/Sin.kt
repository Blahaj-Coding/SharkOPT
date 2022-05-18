package autodiff.operator

import autodiff.*
import autodiff.Vector

import kotlin.math.sin
import kotlin.math.cos

class Sin(val expression: Expression) : Expression() {
    private var containedVariables = expression.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        value = sin(expression.evaluate(variables))
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        expression.solveGradient(variables, gradient, path * cos(expression.value))
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        var g = expression.forwardAutoDiff(variable, value, degree)
        var pSin = Vector()
        var pCos = Vector()
        pSin.add(sin(g[0]))
        pCos.add(cos(g[0]))
        for (k in 1..degree) {
            var ckSin = 0.0
            var ckCos = 0.0
            for (i in 1..k) {
                ckSin += i * g[i] * pCos[k - i]
                ckCos += i * g[i] * pSin[k - i]
            }
            pSin.add(ckSin / k)
            pCos.add(-ckCos / k)
        }
        return pSin
    }

    override fun toString(): String {
        return "sin($expression)"
    }
}