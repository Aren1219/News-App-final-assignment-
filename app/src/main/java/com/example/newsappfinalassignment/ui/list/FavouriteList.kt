package com.example.newsappfinalassignment.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.ui.MainViewModel
import com.example.newsappfinalassignment.util.Screen

@Composable
fun FavouriteScreen(
    viewModel: MainViewModel,
    navHostController: NavHostController,
    signOut: () -> Unit
){
    var alert by remember { mutableStateOf(false) }
    var deleteData: Data? by remember { mutableStateOf(null)}

    if (alert){
        AlertDialog(

            onDismissRequest = {alert = false},
            title = { Text(text = "Are you sure?") },
            text = { Text(text = "You may not find the article anymore")},
            confirmButton = {
                Button(onClick = {
                    if (deleteData != null) {
                        viewModel.deleteNews(deleteData!!)
                        deleteData = null
                        alert = false
                    }
                }) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                Button(onClick = { alert = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    Scaffold(topBar = {
        Surface(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.End) {
                Button(onClick = { signOut() }) {
                    Text(text = "Sign out")
                }
            }
        }
    }
    ) { innerPadding ->
        val list = viewModel.savedList.observeAsState()
        if (list.value.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved news",
                    style = MaterialTheme.typography.h5
                )
            }
        } else {
            NewsListUi(
                list = list.value!!,
                loadMore = {},
                onSelect = {uuid -> navHostController.navigate(
                    Screen.NewsDetails.path + Screen.SavedNews.title + "/" + uuid
                )},
                onSave = {
                        data -> deleteData = data
                        alert = true
                         },
                listState = rememberLazyListState(),
                icon = Icons.Default.Delete
            )
        }
    }
}