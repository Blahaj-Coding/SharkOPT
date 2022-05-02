package autodiff

class Quotient(val numerator: Expression, val denominator: Expression) : Expression() {
    var containedVariables: Set<Variable> = numerator.getVariables() + denominator.getVariables()

    override fun getVariables(): Set<Variable> {
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
        return "(${numerator} / ${denominator})"
    }
}