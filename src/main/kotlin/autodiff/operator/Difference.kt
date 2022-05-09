package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap

class Difference(private val minuend: Expression, private val subtrahend: Expression) : Expression() {
    private var containedVariables: Set<Variable> = minuend.getVariables() + subtrahend.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        return minuend.evaluate(variables) - subtrahend.evaluate(variables)
    }

    override fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double) {
        minuend.solveJacobian(variables, jacobian, path)
        subtrahend.solveJacobian(variables, jacobian, path * -1)
    }

    override fun toString(): String {
        return "(${minuend} - ${subtrahend})"
    }
}