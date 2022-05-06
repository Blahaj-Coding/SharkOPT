package autodiff

class Constant(val num : Double) : Expression() {
    override fun getVariables(): MutableSet<Variable> {
        return mutableSetOf();
    }

    override fun evaluate(variables: HashMap<Variable, Double>): Double {
        return num
    }

    override fun solveJacobian(variables: HashMap<Variable, Double>, jacobian: HashMap<Variable, Double>, path: Double) {}

    override fun toString(): String {
        return "$num"
    }
}