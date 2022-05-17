package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap
import autodiff.Vector

import kotlin.math.sin
import kotlin.math.cos

class Cos(private val expression: Expression) : Expression() {
    private var containedVariables = expression.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        value = cos(expression.evaluate(variables))
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        expression.solveGradient(variables, gradient, path * -sin(expression.value))
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        val g = expression.forwardAutoDiff(variable, value, degree)
        val pSin = Vector()
        val pCos = Vector()
        pSin.add(sin(g.get(0)))
        pCos.add(cos(g.get(0)))
        for (k in 1..degree) {
            var ckSin = 0.0
            var ckCos = 0.0
            for (i in 1..k) {
                ckSin += i * g.get(i) * pCos.get(k - i)
                ckCos += i * g.get(i) * pSin.get(k - i)
            }
            pSin.add(ckSin / k)
            pCos.add(-ckCos / k)
        }
        return pCos
    }

    override fun toString(): String {
        return "cos($expression)"
    }
}