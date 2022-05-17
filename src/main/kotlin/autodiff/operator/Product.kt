package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap
import autodiff.Vector
import kotlin.math.cos

class Product(val expressionOne: Expression, val expressionTwo: Expression) : Expression() {
    private var containedVariables: Set<Variable> = expressionOne.getVariables() + expressionTwo.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        value = expressionOne.evaluate(variables) * expressionTwo.evaluate(variables)
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        expressionOne.solveGradient(variables, gradient, path * expressionTwo.value)
        expressionTwo.solveGradient(variables, gradient, path * expressionOne.value)
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        var p1 = expressionOne.forwardAutoDiff(variable, value, degree)
        var p2 = expressionTwo.forwardAutoDiff(variable, value, degree)
        var p = Vector()
        for (i in 0..degree) {
            var ck = 0.0
            for (j in 0..i) {
                ck += p1.get(j) * p2.get(i - j)
            }
            p.add(ck)
        }
        return p
    }

    override fun toString(): String {
        return "(${expressionOne} * ${expressionTwo})"
    }
}