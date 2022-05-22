package autodiff.operator

import autodiff.*
import org.ejml.simple.SimpleMatrix

class Sum(val left: Expression, val right: Expression) : Expression() {
    private var containedVariables: Set<Variable> = left.getVariables() + right.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: SimpleMatrix): Double {
        value = left.evaluate(variables) + right.evaluate(variables)
        return value
    }

    // df/du (u + v) = 1
    // df/dv (u + v) = 1
    override fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double) {
        left.solveGradient(variables, gradient, path)
        right.solveGradient(variables, gradient, path)
    }

    override fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        val p1 = left.forwardAutoDiff(variable, value, degree)
        val p2 = right.forwardAutoDiff(variable, value, degree)
//        println(p1 + p2)
        return p1 + p2
    }

    override fun toString(): String {
        return "(${left} + ${right})"
    }

    constructor(left: Expression, right: Double) : this(left, Constant(right))
    constructor(left: Double, right: Expression) : this(Constant(left), right)
    constructor(left: Double, right: Double) : this(Constant(left), Constant(right))
    constructor(left: Expression, right: Int) : this(left, Constant(right))
    constructor(left: Int, right: Expression) : this(Constant(left), right)
    constructor(left: Int, right: Int) : this(Constant(left), Constant(right))
    constructor(left: Double, right: Int) : this(Constant(left), Constant(right))
    constructor(left: Int, right: Double) : this(Constant(left), Constant(right))
}