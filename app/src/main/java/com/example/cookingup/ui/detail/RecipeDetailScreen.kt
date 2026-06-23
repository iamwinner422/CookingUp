package com.example.cookingup.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cookingup.data.model.Recipe
import com.example.cookingup.data.model.sampleRecipes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(recipeId: Int, onBack: () -> Unit) {
    val recipe: Recipe = sampleRecipes.find { it.id == recipeId } ?: return

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(recipe.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton (onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Retour")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = PaddingValues(top = padding.calculateTopPadding(), bottom = 32.dp)) {
            item {
                AsyncImage(model = recipe.imageUrl, contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(250.dp),
                    contentScale = ContentScale.Crop)
            }
            item {
                Column(Modifier.padding(16.dp)) {
                    Text(recipe.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(8.dp))
                    Text("⏱ ${recipe.prepTimeMin} min  ·  👥 ${recipe.servings} personnes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            item {
                Text("Ingrédients", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }
            items(recipe.ingredients) { ing ->
                Text("• ${ing.quantity} ${ing.unit} de ${ing.name}",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp))
            }
            item {
                Text("Préparation", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp))
            }
            itemsIndexed(recipe.steps) { i, step ->
                Text("${i + 1}. $step",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
            }
        }
    }
}