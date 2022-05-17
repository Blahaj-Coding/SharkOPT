package autodiff

import kotlin.math.cos

class Constant(val num : Double) : Expression() {
    override var value = num

    override fun getVariables(): MutableSet<Variable> {
        return mutableSetOf();
    }

    override fun evaluate(variables: VariableMap): Double {
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {}

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        var vector = Vector()
        vector.add(this.value)
        for (k in 1..degree) {
            vector.add(0.0)
        }
        return vector
    }

    override fun toString(): String {
        return "$num"
    }
}