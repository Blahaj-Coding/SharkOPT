package autodiff

import kotlin.math.cos

class Constant(val num : Double) : Expression() {
    override var value = num

    override fun getVariables(): MutableSet<Variable> {
        return mutableSetOf();
    }

    override fun evaluate(variables: Vector): Double {
        return value
    }

    override fun solveGradient(variables: Vector, gradient: Vector, path: Double) {}

    override fun forwardAutoDiff(variable: Variable, value: Vector, degree: Int): Vector {
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