package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap
import autodiff.Vector
import kotlin.math.cos

class Difference(val left: Expression, val right: Expression) : Expression() {
    private var containedVariables: Set<Variable> = left.getVariables() + right.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: Vector): Double {
        value = left.evaluate(variables) - right.evaluate(variables)
        return value
    }

    // df/du (u - v) = 1
    // df/dv (u - v) = -1
    override fun solveGradient(variables: Vector, gradient: Vector, path: Double) {
        left.solveGradient(variables, gradient, path)
        right.solveGradient(variables, gradient, path * -1)
    }

    override fun forwardAutoDiff(variable: Variable, value: Vector, degree: Int): Vector {
        val p1 = left.forwardAutoDiff(variable, value, degree)
        val p2 = right.forwardAutoDiff(variable, value, degree)
        return p1.minus(p2)
    }
    override fun toString(): String {
        return "(${left} - ${right})"
    }
}