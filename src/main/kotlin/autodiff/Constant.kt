package autodiff

class Constant(val num : Double) : Expression() {
    override fun getVariables(): MutableSet<Variable> {
        return mutableSetOf();
    }
}