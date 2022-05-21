package autodiff

import org.ejml.simple.SimpleMatrix

class Variable(): Expression() {
    override var value = 0.0
    private var initial = 0.0
    var index = 0

//    constructor(index: Int, initialGuess: Double) : this(index) {
//        this.initial = initialGuess
//    }

//    be smart not dumb
    fun setInitial(initial: Double) {
        this.initial = initial
    }

    fun getInitial() : Double {
        return initial
    }

    override fun getVariables(): Set<Variable> {
        return setOf(this)
    }

    override fun evaluate(variables: SimpleMatrix): Double {
        value = variables[index]
        return value
    }

    override fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double) {
        gradient[index] += path
    }

    override fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        var vector = SimpleMatrix(degree + 1,1)
        vector[0] = value[index]
        if (variable == this) vector[1] = 1.0
        else vector[1] = 0.0
        for (i in 2..degree) {
            vector[i] = 0.0
        }
        return vector
    }

    override fun toString(): String {
        return "v$index"
    }
}
