package autodiff

import java.util.Collections

class Variable(val name: String): Expression() {
    override fun getVariables(): Set<Variable> {
        return setOf(this)
    }

    override fun evaluate(variables: VariableMap): Double {
        return variables.get(this)
    }

    override fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double) {
        jacobian.put(this, jacobian.get(this) + path)
    }

    override fun toString(): String {
        return name
    }
}