package autodiff.operator

import autodiff.*
import org.ejml.simple.SimpleMatrix
import kotlin.math.cos

class Product(val left: Expression, val right: Expression) : Expression() {
    private var containedVariables: Set<Variable> = left.getVariables() + right.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: SimpleMatrix): Double {
        value = left.evaluate(variables) * right.evaluate(variables)
        return value
    }

    // df/du (u * v) = v
    // df/dv (u * v) = u
    override fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double) {
        left.solveGradient(variables, gradient, path * right.value)
        right.solveGradient(variables, gradient, path * left.value)
    }

    override fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        var p1 = left.forwardAutoDiff(variable, value, degree)
        var p2 = right.forwardAutoDiff(variable, value, degree)
        var p = SimpleMatrix(degree + 1,1)
        for (k in 0..degree) {
            var ck = 0.0
            for (j in 0..k) {
                ck += p1[j] * p2[k - j]
            }
            p[k] = ck
        }
        return p
    }

    override fun toString(): String {
        return "(${left} * ${right})"
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