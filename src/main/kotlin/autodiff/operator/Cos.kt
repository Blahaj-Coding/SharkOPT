package autodiff.operator

import autodiff.Expression
import autodiff.Variable

import kotlin.math.sin
import kotlin.math.cos

class Cos(private val expression: Expression) : Expression() {
    var containedVariables = expression.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: HashMap<Variable, Double>): Double {
        return cos(expression.evaluate(variables))
    }

    override fun solveJacobian(variables: HashMap<Variable, Double>, jacobian: HashMap<Variable, Double>, path: Double) {
        expression.solveJacobian(variables, jacobian, path * -sin(expression.evaluate(variables)))
    }

    override fun toString(): String {
        return "cos($expression)"
    }
}