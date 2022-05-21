package autodiff

import org.ejml.simple.SimpleMatrix

class Constant(val num : Double) : Expression() {
    constructor(num: Int) : this(num.toDouble())
    override var value = num

    override fun getVariables(): MutableSet<Variable> {
        return mutableSetOf()
    }

    override fun evaluate(variables: SimpleMatrix): Double {
        return value
    }

    override fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double) {}

    override fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        var vector = SimpleMatrix(degree + 1, 1)
        vector[0] = this.value
        for (k in 1..degree) {
            vector[k] = 0.0
        }
        return vector
    }

    override fun toString(): String {
        return "$num"
    }
}