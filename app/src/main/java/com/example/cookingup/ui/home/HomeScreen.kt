package com.example.cookingup.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookingup.data.model.sampleRecipes
import com.example.cookingup.ui.components.RecipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onRecipeClick: (Int) -> Unit = {}
) {
    Scaffold (
        topBar = {
            TopAppBar(title = { Text("🍳 RecipeApp", style = MaterialTheme.typography.headlineSmall) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Recettes du jour", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            sampleRecipes.forEach { recipe ->
                RecipeCard(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id) },
                    modifier = Modifier
                )
            }
        }
    }
}
