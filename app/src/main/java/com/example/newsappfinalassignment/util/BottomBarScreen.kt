package com.example.newsappfinalassignment.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null,
    val path: String? = null
) {
    object NewsList: Screen(
        route = "news list",
        title = "News",
        icon = Icons.Default.Home
    )
    object SavedNews: Screen(
        route = "saved news",
        title = "Saved News",
        icon = Icons.Default.Favorite
    )
    object NewsDetails: Screen(
        route = "news details/{uuid}",
        title = "News",
        path = "news details/"
    )
}
