package com.example.cookingup.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object RecipeListRoute

@Serializable
data class RecipeDetailRoute(
    val recipeId: Int
)

@Serializable
data object FavoritesRoute

@Serializable
data object CreateRoute