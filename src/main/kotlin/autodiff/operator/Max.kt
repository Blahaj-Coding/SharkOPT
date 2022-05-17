package autodiff.operator

import autodiff.*

class Max(private val a: Expression, private val b: Expression) : Expression() {
    private val containedVariables = a.getVariables() + b.getVariables()

    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        val a0 = a.evaluate(variables)
        val b0 = b.evaluate(variables)

        if (a0 > b0) a.solveGradient(variables, gradient, path)
        else         b.solveGradient(variables, gradient, path)
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        val a0 = a.evaluate(value)
        val b0 = b.evaluate(value)

        return if (a0 > b0) a.forwardAutoDiff(variable, value, degree)
        else                b.forwardAutoDiff(variable, value, degree)
    }

    override fun evaluate(variables: VariableMap): Double {
        return kotlin.math.max(a.evaluate(variables), b.evaluate(variables))
    }

    override fun toString(): String {
        return "min($a, $b)"
    }
}