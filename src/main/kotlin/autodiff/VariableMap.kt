package autodiff

class VariableMap {
    var map: HashMap<Variable, Double>

    init {
        this.map = HashMap<Variable, Double>()
    }

    fun put(variable: Variable, value: Double) {
        this.map.put(variable, value)
    }

    fun get(key: Variable): Double {
        return this.map.get(key)!!
    }

    fun plus(addend: VariableMap): VariableMap {
        var newMap = VariableMap()
        for (key in this.map.keys) {
            newMap.put(key, get(key) + addend.get(key))
        }
        return newMap
    }

    fun times(scale: Double): VariableMap {
        var newMap = VariableMap()
        for (key in this.map.keys) {
            newMap.put(key, get(key) * scale)
        }
        return newMap
    }

    fun copy(): VariableMap {
        var newMap = VariableMap()
        newMap.map = HashMap<Variable, Double>(this.map)
        return newMap
    }

    override fun toString(): String {
        return map.toString()
    }
}