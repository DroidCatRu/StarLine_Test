package ru.droidcat.starline.core.navigation

interface FeatureIntent

data class NavigateIntent(val route: String) : FeatureIntent
