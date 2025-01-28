package com.example.myrecipeapp

sealed class Screen(val route: String) {
    object RecipeScreen : Screen("recipe")

    object DetailScreen : Screen("detail")
}

