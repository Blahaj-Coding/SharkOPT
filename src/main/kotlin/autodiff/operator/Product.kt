package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap

class Product(val expressionOne: Expression, val expressionTwo: Expression) : Expression() {
    private var containedVariables: Set<Variable> = expressionOne.getVariables() + expressionTwo.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        return expressionOne.evaluate(variables) * expressionTwo.evaluate(variables)
    }

    override fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double) {
        expressionOne.solveJacobian(variables, jacobian, path * expressionTwo.evaluate(variables))
        expressionTwo.solveJacobian(variables, jacobian, path * expressionOne.evaluate(variables))
    }

    override fun toString(): String {
        return "(${expressionOne} * ${expressionTwo})"
    }
}