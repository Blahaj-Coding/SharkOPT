package autodiff

abstract class Expression {
    abstract fun getVariables(): MutableSet<Variable>

    operator fun plus(expression : Expression) : Expression {
        return Sum(this, expression)
    }

//    operator fun plus(expression : Expression) : Expression {
//        return Sum(this, expression)
//    }

    operator fun times(expression : Expression) : Expression {
        return Product(this, expression)
    }

    operator fun div(expression : Expression) : Expression {
        return Quotient(this, expression)
    }
}