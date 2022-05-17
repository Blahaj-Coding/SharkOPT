# HelixOptimizer

Join the discord and contribute! https://discord.gg/kqe74EER

A non-linear problem solver using automatic differentiation and penalty methods.

## Automatic Differentiation

Let's say you want to take the partial derivative of some function,
and let's call that function `f`. You could try to evaluate that partial
derivative with a difference quotient: `df/dx = (f(x + h) - f(x))/h`
where `h` is some very small number. However, this runs into the problem
of finding the optimal value for `h` to reduce the amount of error in the
calculation and requires as many operations as the number of variables you wish. 

