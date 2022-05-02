import autodiff.*

fun main(args: Array<String>) {
    var variable: Variable = Variable("variable")
    var second: Variable = Variable("second")
    var duck: Variable = Variable("duck")
    var exp: Expression = duck + second / (variable + (duck * variable) + second)
    var variables: MutableSet<Variable> = exp.getVariables()

    println(exp)
}