package autodiff

import kotlin.math.sqrt

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

    fun norm(): Double {
        var norm = 0.0
        for (item in this.map.values) {
            norm += item!! * item!!
        }
        return sqrt(norm)
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