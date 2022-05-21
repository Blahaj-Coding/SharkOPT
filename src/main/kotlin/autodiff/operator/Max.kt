package autodiff.operator

import autodiff.*
import org.ejml.simple.SimpleMatrix

class Max(val a: Expression, val b: Expression) : Expression() {
    private val containedVariables = a.getVariables() + b.getVariables()

    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double) {
        val a0 = a.evaluate(variables)
        val b0 = b.evaluate(variables)

        if (a0 > b0) a.solveGradient(variables, gradient, path)
        else         b.solveGradient(variables, gradient, path)
    }

    override fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        val a0 = a.evaluate(value)
        val b0 = b.evaluate(value)

        return if (a0 > b0) a.forwardAutoDiff(variable, value, degree)
        else                b.forwardAutoDiff(variable, value, degree)
    }

    override fun evaluate(variables: SimpleMatrix): Double {
        return kotlin.math.max(a.evaluate(variables), b.evaluate(variables))
    }

    override fun toString(): String {
        return "min($a, $b)"
    }

    constructor(a: Expression, b: Double) : this(a, Constant(b))
    constructor(a: Double, b: Expression) : this(Constant(a), b)
    constructor(a: Double, b: Double) : this(Constant(a), Constant(b))
    constructor(a: Expression, b: Int) : this(a, Constant(b))
    constructor(a: Int, b: Expression) : this(Constant(a), b)
    constructor(a: Int, b: Int) : this(Constant(a), Constant(b))
    constructor(a: Double, b: Int) : this(Constant(a), Constant(b))
    constructor(a: Int, b: Double) : this(Constant(a), Constant(b))
}