package com.example.cookingup.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingup.data.model.Category
import com.example.cookingup.data.model.Recipe
import com.example.cookingup.data.model.filterRecipes
import com.example.cookingup.data.model.sampleRecipes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class RecipesViewModel: ViewModel() {
    private val _query = MutableStateFlow("")
    private val _category = MutableStateFlow(Category.ALL)

    val query: StateFlow<String> = _query.asStateFlow()
    val category: StateFlow<Category> = _category.asStateFlow()

    val recipes: Flow<List<Recipe>> = combine(_query, _category) {
        query, category ->
        filterRecipes(query, category)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = sampleRecipes
    )

    fun onQueryChange(query: String) { _query.value = query }
    fun onCategoryChange(category: Category) { _category.value = category }
}