package autodiff

class Product(val expressionOne: Expression, val expressionTwo: Expression) : Expression() {
    var containedVariables: Set<Variable> = expressionOne.getVariables() + expressionTwo.getVariables()
    val expressions = ArrayList<Expression>()

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

//    init {
//        children.add(factor1)
//        children.add(factor2)
//        factor1.parents.add(this)
//        factor2.parents.add(this)
//        name = "x*y"
//    }
//
//    override fun updateX(): Double {
////        x = factor1.x * factor2.x
//        return x
//    }
//
//    override fun updateXDot(): Double {
//        xDot = factor1.x * factor2.xDot + factor1.xDot * factor2.x
//        return xDot
//    }
//
//    override fun partial(variable: Variable): Double {
//        return if (variable == factor1 && variable == factor2) {
//            2 * factor1.x
//        } else if (variable == factor1) {
//            factor2.x
//        } else {
//            factor1.x
//        }
//    }
//
////    override fun equals(other: Any?): Boolean {
////        return if (other != null && other is Product) {
////            other.factor1 == factor1 && other.factor2 == factor2
////        } else {
////            false
////        }
////    }
    override fun toString(): String {
    return "(${expressionOne} * ${expressionTwo})"
    }
}