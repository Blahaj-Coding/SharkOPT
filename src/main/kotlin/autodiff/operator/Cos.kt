package autodiff.operator

import autodiff.Constant
import autodiff.Expression
import autodiff.Variable
import autodiff.Vector
import org.ejml.simple.SimpleMatrix

import kotlin.math.sin
import kotlin.math.cos

class Cos(val expression: Expression) : Expression() {
    private var containedVariables = expression.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: SimpleMatrix): Double {
        value = cos(expression.evaluate(variables))
        return value
    }

    // df/du cos(u) = -sin(u)
    override fun solveGradient(variables: SimpleMatrix, gradient: SimpleMatrix, path: Double) {
        expression.solveGradient(variables, gradient, path * -sin(expression.value))
    }

    override fun forwardAutoDiff(variable: Variable, value: SimpleMatrix, degree: Int): SimpleMatrix {
        var g = expression.forwardAutoDiff(variable, value, degree)
        var pSin = SimpleMatrix(degree + 1,1)
        var pCos = SimpleMatrix(degree + 1,1)
        pSin[0] = sin(g[0])
        pCos[0] = cos(g[0])
        for (k in 1..degree) {
            var ckSin = 0.0
            var ckCos = 0.0
            for (i in 1..k) {
                ckSin += i * g[i] * pCos[k - i]
                ckCos += i * g[i] * pSin[k - i]
            }
            pSin[k] = ckSin / k
            pCos[k] = -ckCos / k
        }
        return pCos
    }

    override fun toString(): String {
        return "cos($expression)"
    }

    constructor(expression: Double) : this(Constant(expression))
    constructor(expression: Int) : this(Constant(expression))
}