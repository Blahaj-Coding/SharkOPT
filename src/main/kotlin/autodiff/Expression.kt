package autodiff

import autodiff.autodiff.Equation
import autodiff.operator.*
import org.ejml.simple.SimpleMatrix

abstract class Expression {
    abstract var value: Double

    abstract fun getVariables(): Set<Variable>

    operator fun plus(expression : Expression) = Sum(this, expression)
    operator fun plus(value : Double) = Sum(this, Constant(value))
    operator fun plus(value : Int) = Sum(this, Constant(value))

    operator fun minus(expression : Expression) = Difference(this, expression)
    operator fun minus(value : Double) = Difference(this, Constant(value))
    operator fun minus(value : Int) = Difference(this, Constant(value))

    operator fun times(expression : Expression) = Product(this, expression)
    operator fun times(value : Double) = Product(this, Constant(value))
    operator fun times(value: Int) = Product(this, Constant(value))

    operator fun div(expression : Expression) = Quotient(this, expression)
    operator fun div(value : Double) = Quotient(this, Constant(value))
    operator fun div(value : Int) = Quotient(this, Constant(value))

    /**
     * operator should be == (in cpp)
     */
    fun isEqualTo(other: Expression) : Equation {
        return Equation(this - other, operator = Equation.Operator.EQUAL_TO_ZERO)
    }
    fun isEqualTo(other: Double): Equation = isEqualTo(Constant(other))
    fun isEqualTo(other: Int): Equation = isEqualTo(Constant(other))

    /**
     * operator should be < (in cpp)
     */
    fun isLessThan(other: Expression) : Equation {
        return Equation(this - other, operator = Equation.Operator.LESS_THAN_ZERO)
    }

    fun isLessThan(other: Double): Equation = isLessThan(Constant(other))
    fun isLessThan(other: Int): Equation = isLessThan(Constant(other))


    /**
     * operator should be > (in cpp)
     */
    fun isGreaterThan(other: Expression) : Equation {
        return Equation(other - this, operator = Equation.Operator.LESS_THAN_ZERO)
    }

    fun isGreaterThan(other: Double): Equation = isGreaterThan(Constant(other))
    fun isGreaterThan(other: Int): Equation = isGreaterThan(Constant(other))

    /**
     * operator should be <= (in cpp)
     */
    fun isLessThanOrEqualTo(other: Expression) : Equation {
        return Equation(this - other, operator = Equation.Operator.LESS_THAN_OR_EQUAL_TO_ZERO)
    }

    fun isLessThanOrEqualTo(other: Double): Equation = isLessThanOrEqualTo(Constant(other))
    fun isLessThanOrEqualTo(other: Int): Equation = isLessThanOrEqualTo(Constant(other))

    /**
     * operator should be >= (in cpp)
     */
    fun isGreaterThanOrEqualTo(other: Expression) : Equation {
        return Equation(other - this, operator = Equation.Operator.LESS_THAN_OR_EQUAL_TO_ZERO)
    }

    fun isGreaterThanOrEqualTo(other: Double): Equation = isLessThanOrEqualTo(Constant(other))
    fun isGreaterThanOrEqualTo(other: Int): Equation = isLessThanOrEqualTo(Constant(other))

    abstract fun evaluate(variables: SimpleMatrix): Double

    abstract fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double)

    abstract fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix

    /** Use this method for getting derivatives, forwardAutoDiff returns the coefficients of the expression's
     *  Taylor polynomial which require multiplication by n! to yield the proper derivatives.
     */
    fun solveDerivatives(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        var coef = forwardAutoDiff(variable, value, degree)
        var nFact = 1.0
        for (k in 1 until coef.numRows()) {
            nFact *= k
            coef[k] *= nFact
        }
        return coef
    }

    /**
     * Solves the gradient of the expression.
     *
     * @param variables an n x 1 matrix representing the values of all variables.
     */
    fun solveGradient(variables: SimpleMatrix): SimpleMatrix {
        var gradient = SimpleMatrix(variables.numRows(), 1)
        for (k in 0 until variables.numRows()) gradient.set(k, 0, 0.0)
        evaluate(variables)
        solveGradient(variables, gradient, 1.0)
        return gradient
    }
}