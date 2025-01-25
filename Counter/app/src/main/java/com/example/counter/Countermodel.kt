package com.example.counter


data class Countermodel(var count: Int = 0)

class CounterRepository {
    private val _counter = Countermodel(0)

    fun getCounter() = _counter

    fun increament() {
        _counter.count++
    }

    fun decreament() {
        _counter.count--
    }
}