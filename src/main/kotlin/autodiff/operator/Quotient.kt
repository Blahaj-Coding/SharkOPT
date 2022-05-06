package autodiff.operator

import autodiff.Expression
import autodiff.Variable

class Quotient(val numerator: Expression, val denominator: Expression) : Expression() {
    var containedVariables: Set<Variable> = numerator.getVariables() + denominator.getVariables()
    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: HashMap<Variable, Double>): Double {
        return numerator.evaluate(variables) / denominator.evaluate(variables)
    }

    override fun solveJacobian(variables: HashMap<Variable, Double>, jacobian: HashMap<Variable, Double>, path: Double) {
        numerator.solveJacobian(variables, jacobian, path)
        denominator.solveJacobian(variables, jacobian, path)
    }

    override fun toString(): String {
        return "(${numerator} / ${denominator})"
    }
}