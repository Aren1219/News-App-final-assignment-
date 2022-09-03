package com.example.newsappfinalassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsappfinalassignment.ui.MainViewModel
import com.example.newsappfinalassignment.ui.list.NewsListScreen
import com.example.newsappfinalassignment.ui.theme.NewsAppfinalAssignmentTheme
import com.example.newsappfinalassignment.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppfinalAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val navController = rememberNavController()
    val items = listOf(Screen.NewsList, Screen.SavedNews)

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route){
                                popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                                launchSingleTop = true
                                restoreState = true
                                }
                            },
                        icon = { Icon(screen.icon!!, null) },
                        label = { Text(text = screen.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.NewsList.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.NewsList.route) { NewsListScreen(
                viewModel = viewModel,
                navHostController = navController
            ) }
            composable(Screen.SavedNews.route) {}
        }
    }
}

