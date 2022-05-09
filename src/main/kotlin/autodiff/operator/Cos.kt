package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap

import kotlin.math.sin
import kotlin.math.cos

class Cos(private val expression: Expression) : Expression() {
    private var containedVariables = expression.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        return cos(expression.evaluate(variables))
    }

    override fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double) {
        expression.solveJacobian(variables, jacobian, path * -sin(expression.evaluate(variables)))
    }

    override fun toString(): String {
        return "cos($expression)"
    }
}