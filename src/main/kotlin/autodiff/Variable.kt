package autodiff

class Variable(val name: String): Expression() {
    override var value = 0.0
    var equalExpression: Expression = this

    override fun getVariables(): Set<Variable> {
        return setOf(this)
    }

    override fun evaluate(variables: VariableMap): Double {
        value = variables.get(this)
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        gradient.put(this, gradient.get(this) + path)
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        if (equalExpression != this) {
            return equalExpression.forwardAutoDiff(variable, value, degree)
        }
        var vector = Vector()
        vector.add(value.get(this))
        if (variable == this) vector.add(1.0)
        else vector.add(0.0)
        for (i in 2..degree) {
            vector.add(0.0)
        }
        return vector
    }

    fun setEqual(expression: Expression) {
        equalExpression = expression
    }

    override fun toString(): String {
        return name
    }
}