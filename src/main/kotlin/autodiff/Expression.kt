package autodiff

import autodiff.operator.Difference
import autodiff.operator.Product
import autodiff.operator.Quotient
import autodiff.operator.Sum

abstract class Expression {
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

    abstract fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double)

    fun solveJacobian(variables: VariableMap): VariableMap {
        var jacobian = VariableMap()
        getVariables().map {jacobian.put(it, 0.0)}
        solveJacobian(variables, jacobian, 1.0)
        return jacobian
    }
}