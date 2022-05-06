package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import kotlin.math.ln
import kotlin.math.log

class Log(private val base: Expression, private val expression: Expression) : Expression() {
    private var containedVariables = base.getVariables() + expression.getVariables()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: HashMap<Variable, Double>): Double {
        return log(expression.evaluate(variables), base.evaluate(variables))
    }

    override fun solveJacobian(variables: HashMap<Variable, Double>, jacobian: HashMap<Variable, Double>, path: Double) {
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