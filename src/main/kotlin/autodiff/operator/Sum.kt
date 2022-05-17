package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap
import autodiff.Vector

class Sum(private val expressionOne: Expression, private val expressionTwo: Expression) : Expression() {
    private var containedVariables: Set<Variable> = expressionOne.getVariables() + expressionTwo.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        value = expressionOne.evaluate(variables ) + expressionTwo.evaluate(variables)
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        expressionOne.solveGradient(variables, gradient, path)
        expressionTwo.solveGradient(variables, gradient, path)
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        val p1 = expressionOne.forwardAutoDiff(variable, value, degree)
        val p2 = expressionTwo.forwardAutoDiff(variable, value, degree)
        return p1.plus(p2)
    }

    override fun toString(): String {
        return "(${expressionOne} + ${expressionTwo})"
    }
}