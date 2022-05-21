package autodiff.autodiff

import autodiff.Expression

class Equation(val expression: Expression, val operator: Operator) {
    enum class Operator {
        EQUAL_TO_ZERO,
        LESS_THAN_ZERO,
        LESS_THAN_OR_EQUAL_TO_ZERO,
    }
}