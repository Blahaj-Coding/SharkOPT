package autodiff

class Constant(val num : Double) : Expression() {
    override fun getVariables(): MutableSet<Variable> {
        return mutableSetOf();
    }

    override fun evaluate(variables: VariableMap): Double {
        return num
    }

    override fun solveJacobian(variables: VariableMap, jacobian: VariableMap, path: Double) {}

    override fun toString(): String {
        return "$num"
    }
}