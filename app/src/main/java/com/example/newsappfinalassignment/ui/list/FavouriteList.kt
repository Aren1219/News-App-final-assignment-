package com.example.newsappfinalassignment.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.ui.MainViewModel
import com.example.newsappfinalassignment.ui.theme.NewsAppfinalAssignmentTheme
import com.example.newsappfinalassignment.util.Screen

@Composable
fun FavouriteScreen(
    viewModel: MainViewModel,
    navHostController: NavHostController,
    signOut: () -> Unit
){
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
                onSave = {data -> viewModel.deleteNews(data)},
                listState = rememberLazyListState(),
            )
        }
    }
}