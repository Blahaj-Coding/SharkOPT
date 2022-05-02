package autodiff

import java.util.Collections

class Variable(val name: String): Expression() {

    var x = 0.0
    var xDot = 0.0
    var xBar = 0.0

    override fun getVariables(): MutableSet<Variable> {
        return mutableSetOf(this)
    }

    open fun updateX(): Double {
        return x
    }

    open fun updateXDot(): Double {
        return xDot
    }

    open fun partial(variable: Variable): Double {
        return 1.0
    }

//    fun updateXBar(): Double {
//        for (parent in parents) {
//            xBar += parent.xBar * parent.partial(this)
//        }
//        return xBar
//    }
//
    override fun toString(): String {
        return name
    }
}