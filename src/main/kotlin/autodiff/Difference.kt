//package autodiff
//
//class Difference(val minuend: Variable, val subtrahend: Variable) : Expression {
//
//    init {
//        children.add(minuend)
//        children.add(subtrahend)
//        minuend.parents.add(this)
//        subtrahend.parents.add(this)
//        name = "x-y"
//    }
//
//    override fun updateX(): Double {
//        x = minuend.x - subtrahend.x
//        return x
//    }
//
//    override fun updateXDot(): Double {
//        xDot = minuend.xDot - subtrahend.xDot
//        return xDot
//    }
//
//    override fun partial(variable: Variable): Double {
//        return if (variable == minuend && variable == subtrahend) {
//            0.0
//        } else if (variable == minuend) {
//            1.0
//        } else {
//            -1.0
//        }
//    }
//
//    override fun toString(): String {
//        return "$name = ${minuend.name} - ${subtrahend.name} -- ${super.toString()}"
//    }
//}