import autodiff.*

fun main(args: Array<String>) {
    var variable = Variable("variable")
    var second = Variable("second")
    var duck = Variable("duck")
    var exp = duck + second / (variable + (duck * variable) - second)
    var variables = exp.getVariables()

    println(exp)
}