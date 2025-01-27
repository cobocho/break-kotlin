package com.example.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class RecipeState(
    val loading: Boolean = false,
    val list: List<Category> = emptyList(),
    val error: String? = null
)

class MainViewModel : ViewModel() {
    private val _categoriesState = mutableStateOf(RecipeState())

    val categoriesState: State<RecipeState> = _categoriesState

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                val categories = recipeService.getCategories()

                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = categories.categories,
                    error = null
                )
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching categories ${e.message}"
                )
            }
        }
    }
}