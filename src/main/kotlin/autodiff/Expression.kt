package autodiff

import autodiff.operator.Difference
import autodiff.operator.Product
import autodiff.operator.Quotient
import autodiff.operator.Sum

abstract class Expression {
    abstract var value: Double

    abstract fun getVariables(): Set<Variable>

    operator fun plus(expression : Expression) = Sum(this, expression)
    operator fun plus(value : Double) = Sum(this, Constant(value))

    operator fun minus(expression : Expression) = Difference(this, expression)
    operator fun minus(value : Double) = Difference(this, Constant(value))

    operator fun times(expression : Expression) = Product(this, expression)
    operator fun times(value : Double) = Product(this, Constant(value))

    operator fun div(expression : Expression) = Quotient(this, expression)
    operator fun div(value : Double) = Quotient(this, Constant(value))

    abstract fun evaluate(variables: Vector): Double

    abstract fun solveGradient(variables: Vector, gradient: Vector, path: Double)

    abstract fun forwardAutoDiff(variable: Variable, value: Vector, degree: Int): Vector

    /** Use this method for getting derivatives, forwardAutoDiff returns the coefficients of the expression's
     *  Taylor polynomial which require multiplication by n! to yield the proper derivatives.
     */
    fun solveDerivatives(variable: Variable, value: Vector, degree: Int): Vector {
        var coef = forwardAutoDiff(variable, value, degree)
        var nFact = 1.0
        for (k in 1 until coef.values.size) {
            nFact *= k
            coef[k] *= nFact
        }
        return coef
    }

    fun solveGradient(variables: Vector): Vector {
        var gradient = Vector()
        for (k in 1..variables.values.size) gradient.add(0.0)
        evaluate(variables)
        solveGradient(variables, gradient, 1.0)
        return gradient
    }
}