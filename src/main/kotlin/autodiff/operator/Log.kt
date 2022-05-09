package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap
import kotlin.math.ln
import kotlin.math.log

class Log(private val base: Expression, private val expression: Expression) : Expression() {
    private var containedVariables = base.getVariables() + expression.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        return log(expression.evaluate(variables), base.evaluate(variables))
    }

    override fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double) {
        val baseValue = base.evaluate(variables)
        val expValue = expression.evaluate(variables)
        val baseGradient = -log(expValue, baseValue) / (baseValue * ln(baseValue))
        val expGradient = 1 / (expValue * ln(baseValue))
        base.solveJacobian(variables, jacobian, path * baseGradient)
        expression.solveJacobian(variables, jacobian, path * expGradient)
    }

    override fun toString(): String {
        return "log_(${base})[$expression]"
    }
}