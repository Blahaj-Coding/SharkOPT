// Testing... a general non-linear solver appears to be quite difficult
//
 package autodiff.solver.unconstrained

import autodiff.*

abstract class NLPSolver() {
    protected val initialGuess = Vector()
    protected var variables = mutableListOf<Variable>()
    private var index = 0
    var maxIterations = 3000
    var cost: Expression = Constant(0.0)

    fun variable(initialGuess: Double): Variable {
        val variable = Variable(index)
        this.index++
        this.initialGuess.add(initialGuess)
        this.variables.add(variable)
        return variable
    }

    fun variable(): Variable {
        return variable(0.0)
    }

    fun setInitialValue(variable: Variable, value: Double) {
        initialGuess[variable.index] = value
    }

    fun configure(maxIterations: Int = this.maxIterations) {
        this.maxIterations = maxIterations
    }

    /**
     * Try to make sure that user don't break the solver doing weird things.
     */
    protected fun scopeSafety(expression: Expression) {
        for (variable in expression.getVariables()) {
            if (!variables.contains(variable))
                throw Exception("You did a big dumb.... Please only include variables defined in this solver's scope.")
        }
    }

    protected fun vectorToMap(vector: Vector) : MutableMap<Variable, Double>{
        var vectorMap = mutableMapOf<Variable, Double>()
        for ((variable, value) in variables.zip(vector.values)) vectorMap.put(variable, value)
        return vectorMap
    }

    open fun minimize(cost: Expression) {
        scopeSafety(cost)
        this.cost = cost
    }

    abstract fun solve(): MutableMap<Variable, Double>
}