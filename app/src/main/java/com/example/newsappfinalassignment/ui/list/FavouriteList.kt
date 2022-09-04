package com.example.newsappfinalassignment.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.ui.MainViewModel
import com.example.newsappfinalassignment.ui.theme.NewsAppfinalAssignmentTheme

@Composable
fun FavouriteScreen(
    viewModel: MainViewModel,
    navHostController: NavHostController,
){
    val list = viewModel.savedList.observeAsState()
    if (list.value.isNullOrEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "No saved news",
                style = MaterialTheme.typography.h5
            )
        }
    } else {
        NewsListUi(
            list = list.value!!,
            loadMore = {},
            onSelect = {},
            onSave = {data -> viewModel.deleteNews(data)},
            listState = rememberLazyListState()
        )
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewFavourite(){
//    NewsAppfinalAssignmentTheme() {
//
//    }
//}