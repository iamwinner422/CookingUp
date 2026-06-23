package com.example.cookingup.data.model


enum class Category(
    val label: String,
    val emoji: String,
) {
    ALL("Tout", "🍽️"),
    PASTA("Pâtes", "🍝"),
    SALAD("Salades", "🥗"),
    SOUP("Soupes", "🍲"),
    DESSERT("Desserts", "🍰"),
    MEAT("Viandes", "🥩"),
    VEGETARIAN("Végétarien", "🥦")
}

data class Ingredient(
    val name: String,
    val quantity: Double,
    val unit: String
)

data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val prepTimeMin: Int,
    val servings: Int,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val category: Category,
    val isFavorite: Boolean = false
)

sealed class RecipesUiState {
    data object Loading: RecipesUiState()
    data class Success(val recipes: List<Recipe>): RecipesUiState()
    data class Error(val message: String): RecipesUiState()
}

val sampleRecipes = listOf(
    Recipe(1, "Spaghetti Carbonara", "https://picsum.photos/400/300?1", 20, 4,
        listOf(Ingredient("pâtes", 400.0, "g"), Ingredient("œufs", 3.0, "pièces")),
        listOf("Cuire les pâtes", "Mélanger les œufs", "Combiner"), Category.PASTA),
    Recipe(2, "Salade César", "https://picsum.photos/400/300?2", 10, 2,
        listOf(Ingredient("laitue", 1.0, "tête")),
        listOf("Laver la salade", "Ajouter la sauce"), Category.SALAD),
    Recipe(3, "Tarte aux pommes", "https://picsum.photos/400/300?3", 45, 6,
        listOf(Ingredient("pommes", 4.0, "pièces"), Ingredient("farine", 250.0, "g")),
        listOf("Préparer la pâte", "Éplucher les pommes", "Cuire 30 min"), Category.DESSERT)
)

fun filterRecipes(
    query: String,
    category: Category,
    recipes: List<Recipe> = sampleRecipes
): List<Recipe> = recipes
    .filter { recipe ->
        category == Category.ALL || recipe.category == category
    }
    .filter { recipe ->
        query.isBlank() || recipe.title.contains(query, ignoreCase = true)
    }