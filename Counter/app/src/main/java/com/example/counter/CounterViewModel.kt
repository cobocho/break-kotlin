package com.example.counter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CounterViewModel() : ViewModel() {
    private val _repository: CounterRepository = CounterRepository()

    private val _count = mutableStateOf(_repository.getCounter().count)

    val count: MutableState<Int> = _count

    public fun increament() {
        _repository.increament()
        _count.value = _repository.getCounter().count
    }

    public fun decreament() {
        _repository.decreament()
        _count.value = _repository.getCounter().count
    }
}