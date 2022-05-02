package autodiff

class Quotient(val dividend: Expression, val divisor: Expression) : Expression() {
    var containedVariables: MutableSet<Variable> = mutableSetOf()

    init {
        containedVariables += dividend.getVariables()
        containedVariables += divisor.getVariables()
    }

    override fun getVariables(): MutableSet<Variable> {
        return containedVariables
    }

//    init {
//        children.add(dividend)
//        children.add(divisor)
//        dividend.parents.add(this)
//        divisor.parents.add(this)
//        name = "x/y"
//    }
//
//    override fun updateX(): Double {
//        x = dividend.x / divisor.x
//        return x
//    }
//
//    override fun updateXDot(): Double {
//        xDot = (divisor.x * dividend.xDot - dividend.x * divisor.xDot) / (divisor.x * divisor.x)
//        return xDot
//    }
//
//    override fun partial(variable: Variable): Double {
//        return if (variable == dividend && variable == divisor) {
//            1.0
//        } else if (variable == dividend) {
//            1.0 / divisor.x
//        } else {
//            -(dividend.x / (divisor.x * divisor.x))
//        }
//    }
//
    override fun toString(): String {
        return "(${dividend} / ${divisor})"
    }
}