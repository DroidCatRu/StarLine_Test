package ru.droidcat.starline.core.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument

data class NavigationDestination(
    val route: String,
    val content: @Composable (arguments: Bundle?) -> Unit,
    val arguments: List<NamedNavArgument> = listOf()
)
