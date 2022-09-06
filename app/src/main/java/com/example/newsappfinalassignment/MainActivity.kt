package com.example.newsappfinalassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsappfinalassignment.ui.MainViewModel
import com.example.newsappfinalassignment.ui.detail.DetailsPage
import com.example.newsappfinalassignment.ui.list.FavouriteScreen
import com.example.newsappfinalassignment.ui.list.NewsListScreen
import com.example.newsappfinalassignment.ui.login.LoginScreen
import com.example.newsappfinalassignment.ui.theme.NewsAppfinalAssignmentTheme
import com.example.newsappfinalassignment.util.Screen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    var signedIn by mutableStateOf(false)

    private val GOOGLE_SIGN_IN = 1
    private val auth by lazy {
        Firebase.auth
    }
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            NewsAppfinalAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    signedIn = auth.currentUser != null
                    if (!signedIn) LoginScreen(auth = auth, { signedIn = true }, {googleSignIn()})
                    else Navigation(signOut = {
                        auth.signOut()
                        signedIn = false
                        Toast.makeText(this, "signed out", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    signedIn = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }
}

@Composable
fun Navigation(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    signOut: () -> Unit
){

    val navController = rememberNavController()
    val items = listOf(Screen.NewsList, Screen.SavedNews)

    //list state for news list
    val listState = rememberLazyListState()

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
            composable(Screen.NewsList.route) {
                NewsListScreen(
                    viewModel = viewModel,
                    navHostController = navController,
                    listState = listState
                )
            }
            composable(route = Screen.NewsDetails.route) { entry ->
                val uuid = entry.arguments?.getString("uuid")
                val parent = entry.arguments?.getString("parent")
                if (uuid != null && parent != null) {
                    DetailsPage(
                        parent = parent,
                        uuid = uuid,
                        viewModel = viewModel,
                        navHostController = navController
                    )
                }
            }
            composable(Screen.SavedNews.route) {
                FavouriteScreen(
                    viewModel = viewModel,
                    navHostController = navController,
                    signOut = {signOut()}
                )
            }
        }
    }
}

