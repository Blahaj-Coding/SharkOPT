import autodiff.*

fun main(args: Array<String>) {
    var variable = Variable("variable")
    var second = Variable("second")
    var duck = Variable("duck")

    var mapping = HashMap<Variable, Double>()
    mapping.put(variable, 1.0)

    println(mapping)
//    println(exp)
}