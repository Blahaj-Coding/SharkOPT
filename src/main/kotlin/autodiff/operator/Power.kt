package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap
import kotlin.math.ln
import kotlin.math.pow

class Power(private val base: Expression, private val exponent: Expression): Expression() {

    private var containedVariables = base.getVariables() + exponent.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        return base.evaluate(variables).pow(exponent.evaluate(variables))
    }

    override fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double) {
        var baseJacobian = exponent.evaluate(variables) * base.evaluate(variables).pow(exponent.evaluate(variables) - 1)
        var expJacobian = this.evaluate(variables) * ln(base.evaluate(variables))
        base.solveJacobian(variables, jacobian,path * baseJacobian)
        exponent.solveJacobian(variables, jacobian, path * expJacobian)
    }

    override fun toString(): String {
        return "(${base} ^ (${exponent}))"
    }
}