package autodiff

class Sum(vararg expressions: Expression) : Expression() {
    var containedVariables: MutableSet<Variable> = mutableSetOf()
    val expressions = ArrayList<Expression>()

    init {
        for (expression in expressions) {
            this.expressions.add(expression)
            this.containedVariables += expression.getVariables()
        }
    }

    override fun getVariables(): MutableSet<Variable> {
        return containedVariables
    }

//    init {
//        children.add(addend1)
//        children.add(addend2)
//        addend1.parents.add(this)
//        addend2.parents.add(this)
//        name = "x+y"
//    }

//    override fun updateX(): Double {
//        x = addend1.x + addend2.x
//        return x
//    }
//
//    override fun updateXDot(): Double {
//        xDot = addend1.xDot + addend2.xDot
//        return xDot
//    }
//
//    override fun partial(variable: Variable): Double {
//        return if (addend1 == addend2) {
//            2.0
//        } else {
//            1.0
//        }
//    }

//    override fun equals(other: Any?): Boolean {
//        return if (other != null && other is Sum) {
//            other.addend1 == addend1 && other.addend2 == addend2
//        } else {
//            false
//        }
//    }

    override fun toString(): String {
        var str : String = ""
        for (expression in expressions) {
            str += expression.toString() + " + "
        }
        return "(" + str.substring(0, str.length - 3) + ")"
    }
}