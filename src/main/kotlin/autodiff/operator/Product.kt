package autodiff.operator

import autodiff.Expression
import autodiff.Variable

class Product(val expressionOne: Expression, val expressionTwo: Expression) : Expression() {
    var containedVariables: Set<Variable> = expressionOne.getVariables() + expressionTwo.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: HashMap<Variable, Double>): Double {
        return expressionOne.evaluate(variables) * expressionTwo.evaluate(variables)
    }

    override fun solveJacobian(variables: HashMap<Variable, Double>, jacobian: HashMap<Variable, Double>, path: Double) {
        expressionOne.solveJacobian(variables, jacobian, path)
        expressionTwo.solveJacobian(variables, jacobian, path)
    }

    override fun toString(): String {
        return "(${expressionOne} * ${expressionTwo})"
    }
}