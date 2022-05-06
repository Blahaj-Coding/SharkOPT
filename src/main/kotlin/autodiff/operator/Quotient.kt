package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import kotlin.math.pow

class Quotient(val numerator: Expression, val denominator: Expression) : Expression() {
    var containedVariables: Set<Variable> = numerator.getVariables() + denominator.getVariables()
    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: HashMap<Variable, Double>): Double {
        return numerator.evaluate(variables) / denominator.evaluate(variables)
    }

    override fun solveJacobian(variables: HashMap<Variable, Double>, jacobian: HashMap<Variable, Double>, path: Double) {
        var numeratorGradient = 1 / denominator.evaluate(variables)
        var denominatorGradient = -1 * numerator.evaluate(variables) / denominator.evaluate(variables).pow(2)
        numerator.solveJacobian(variables, jacobian, path * numeratorGradient)
        denominator.solveJacobian(variables, jacobian, path * denominatorGradient)
    }

    override fun toString(): String {
        return "(${numerator} / ${denominator})"
    }
}