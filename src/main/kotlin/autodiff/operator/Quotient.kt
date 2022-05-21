package autodiff.operator

import autodiff.*
import autodiff.Vector
import org.ejml.simple.SimpleMatrix
import kotlin.math.cos
import kotlin.math.pow

class Quotient(val numerator: Expression, val denominator: Expression) : Expression() {
    private var containedVariables: Set<Variable> = numerator.getVariables() + denominator.getVariables()
    override var value = 0.0
    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: SimpleMatrix): Double {
        value = numerator.evaluate(variables) / denominator.evaluate(variables)
        return value
    }

    // df/du (u / v) = 1 / v
    // df/dv (u / v) = -u / v^2
    override fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double) {
        var numeratorGradient = 1 / denominator.value
        var denominatorGradient = -1 * numerator.value / denominator.value.pow(2)
        numerator.solveGradient(variables, gradient, path * numeratorGradient)
        denominator.solveGradient(variables, gradient, path * denominatorGradient)
    }

    override fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        var p1 = numerator.forwardAutoDiff(variable, value, degree)
        var p2 = denominator.forwardAutoDiff(variable, value, degree)
        var p = SimpleMatrix(degree + 1,1)
        for (k in 0..degree) {
            var ck = p1[k]
            for (i in 0 until k) {
                ck -= p[i] * p2[k - i]
            }
            p[k] = ck / p2[0]
        }
        return p
    }

    override fun toString(): String {
        return "(${numerator} / ${denominator})"
    }

    constructor(numerator: Expression, denominator: Double) : this(numerator, Constant(denominator))
    constructor(numerator: Double, denominator: Expression) : this(Constant(numerator), denominator)
    constructor(numerator: Double, denominator: Double) : this(Constant(numerator), Constant(denominator))
    constructor(numerator: Expression, denominator: Int) : this(numerator, Constant(denominator))
    constructor(numerator: Int, denominator: Expression) : this(Constant(numerator), denominator)
    constructor(numerator: Int, denominator: Int) : this(Constant(numerator), Constant(denominator))
    constructor(numerator: Double, denominator: Int) : this(Constant(numerator), Constant(denominator))
    constructor(numerator: Int, denominator: Double) : this(Constant(numerator), Constant(denominator))
}