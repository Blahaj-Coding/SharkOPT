package autodiff

import autodiff.operator.Difference
import autodiff.operator.Product
import autodiff.operator.Quotient
import autodiff.operator.Sum

abstract class Expression {
    abstract var value: Double

    abstract fun getVariables(): Set<Variable>

    operator fun plus(expression : Expression) : Expression {
        return Sum(this, expression)
    }

    operator fun minus(expression : Expression) : Expression {
        return Difference(this, expression)
    }

    operator fun times(expression : Expression) : Expression {
        return Product(this, expression)
    }

    operator fun div(expression : Expression) : Expression {
        return Quotient(this, expression)
    }

    abstract fun evaluate(variables: VariableMap): Double

    abstract fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double)

    abstract fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector

    /** Use this method for getting derivatives, forwardAutoDiff returns the coefficients of the expression's
     *  Taylor polynomial which require multiplication by n! to yield the proper derivatives.
     */
    fun solveDerivatives(variable: Variable, value: VariableMap, degree: Int): Vector {
        var coef = forwardAutoDiff(variable, value, degree)
        var nFact = 1.0
        for (k in 1 until coef.values.size) {
            nFact *= k
            coef.set(k, coef.get(k) * nFact)
        }
        return coef
    }

    fun solveGradient(variables: VariableMap): VariableMap {
        var gradient = VariableMap()
        getVariables().map {gradient.put(it, 0.0)}
        evaluate(variables)
        solveGradient(variables, gradient, 1.0)
        return gradient
    }
}