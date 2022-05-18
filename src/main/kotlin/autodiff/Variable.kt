package autodiff

class Variable(val index: Int): Expression() {
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return setOf(this)
    }

    override fun evaluate(variables: Vector): Double {
        value = variables[index]
        return value
    }

    override fun solveGradient(variables: Vector, gradient: Vector, path: Double) {
        gradient[index] += path
    }

    override fun forwardAutoDiff(variable: Variable, value: Vector, degree: Int): Vector {
        var vector = Vector()
        vector.add(value[index])
        if (variable == this) vector.add(1.0)
        else vector.add(0.0)
        for (i in 2..degree) {
            vector.add(0.0)
        }
        return vector
    }

    override fun toString(): String {
        return "v$index"
    }
}
