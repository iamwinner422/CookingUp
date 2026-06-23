package com.example.cookingup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookingup.ui.home.HomeScreen
import com.example.cookingup.ui.theme.CookingUpTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cookingup.ui.detail.RecipeDetailScreen
import com.example.cookingup.ui.home.RecipeListScreen
import com.example.cookingup.ui.navigation.CreateRoute
import com.example.cookingup.ui.navigation.FavoritesRoute
import com.example.cookingup.ui.navigation.RecipeDetailRoute
import com.example.cookingup.ui.navigation.RecipeListRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookingUpTheme {
                HomeScreen()
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    CookingUpTheme {
        HomeScreen()
    }
}


@Composable
fun RecipeAppNavHost() {
    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentEntry?.destination?.hasRoute(RecipeListRoute::class) == true,
                    onClick  = {
                        navController.navigate(RecipeListRoute) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    icon  = { Icon(Icons.Default.Home, null) },
                    label = { Text("Recettes") }
                )
                NavigationBarItem(
                    selected = currentEntry?.destination?.hasRoute(FavoritesRoute::class) == true,
                    onClick  = {
                        navController.navigate(FavoritesRoute) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    icon  = { Icon(Icons.Default.Favorite, null) },
                    label = { Text("Favoris") }
                )
                NavigationBarItem(
                    selected = currentEntry?.destination?.hasRoute(CreateRoute::class) == true,
                    onClick = {
                        navController.navigate(CreateRoute) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    icon  = { Icon(Icons.Default.Add, null) },
                    label = { Text("Créer") }

                )
            }
        }
    ) { padding ->
        NavHost(
            navController    = navController,
            startDestination = RecipeListRoute,           // un objet, pas une string
            modifier         = Modifier.padding(padding)
        ) {
            composable<RecipeListRoute> {                   // type-safe — pas de string
                RecipeListScreen(
                    onRecipeClick = { id ->
                        navController.navigate(RecipeDetailRoute(recipeId = id))
                    }
                )
            }
            composable<RecipeDetailRoute> { backStack ->
                val route = backStack.toRoute<RecipeDetailRoute>()  // extraction type-safe
                RecipeDetailScreen(
                    recipeId = route.recipeId,
                    onBack   = { navController.popBackStack() }
                )
            }
            composable<FavoritesRoute> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("❤️ Favoris")
                }
            }
            composable<CreateRoute> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ajouter")
                }
            }
        }
    }
}