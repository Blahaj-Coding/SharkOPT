package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap
import autodiff.Vector
import kotlin.math.cos

class Difference(private val minuend: Expression, private val subtrahend: Expression) : Expression() {
    private var containedVariables: Set<Variable> = minuend.getVariables() + subtrahend.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        value = minuend.evaluate(variables) - subtrahend.evaluate(variables)
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        minuend.solveGradient(variables, gradient, path)
        subtrahend.solveGradient(variables, gradient, path * -1)
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        val p1 = minuend.forwardAutoDiff(variable, value, degree)
        val p2 = subtrahend.forwardAutoDiff(variable, value, degree)
        return p1.minus(p2)
    }
    override fun toString(): String {
        return "(${minuend} - ${subtrahend})"
    }
}