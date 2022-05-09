package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap

class Sum(private val expressionOne: Expression, private val expressionTwo: Expression) : Expression() {
    private var containedVariables: Set<Variable> = expressionOne.getVariables() + expressionTwo.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        return expressionOne.evaluate(variables ) + expressionTwo.evaluate(variables)
    }

    override fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double) {
        expressionOne.solveJacobian(variables, jacobian, path)
        expressionTwo.solveJacobian(variables, jacobian, path)
    }

    override fun toString(): String {
        return "(${expressionOne} + ${expressionTwo})"
    }
}