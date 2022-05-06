package autodiff.operator

import autodiff.Expression
import autodiff.Variable

class Difference(val minuend: Expression, val subtrahend: Expression) : Expression() {
    var containedVariables: Set<Variable> = minuend.getVariables() + subtrahend.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: HashMap<Variable, Double>): Double {
        return minuend.evaluate(variables) - subtrahend.evaluate(variables)
    }

    override fun solveJacobian(variables: HashMap<Variable, Double>, jacobian: HashMap<Variable, Double>, path: Double) {
        minuend.solveJacobian(variables, jacobian, path)
        subtrahend.solveJacobian(variables, jacobian, path)
    }

    override fun toString(): String {
        return "(${minuend} - ${subtrahend})"
    }
}