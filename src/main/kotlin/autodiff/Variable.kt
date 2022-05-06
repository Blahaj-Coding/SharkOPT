package autodiff

import java.util.Collections

class Variable(val name: String): Expression() {

    var x = 0.0
    var xDot = 0.0
    var xBar = 0.0

    override fun getVariables(): Set<Variable> {
        return setOf(this)
    }

    override fun evaluate(variables: HashMap<Variable, Double>): Double {
        return variables.get(this)!!
    }

    override fun solveJacobian(variables: HashMap<Variable, Double>, jacobian: HashMap<Variable, Double>, path: Double) {
        jacobian.put(this, jacobian.get(this)!! + path)
    }

    override fun toString(): String {
        return name
    }
}