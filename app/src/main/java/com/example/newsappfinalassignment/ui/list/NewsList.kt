package com.example.newsappfinalassignment.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsappfinalassignment.R
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.ui.MainViewModel
import com.example.newsappfinalassignment.ui.theme.Shapes
import com.example.newsappfinalassignment.util.Resource
import com.skydoves.landscapist.glide.GlideImage

fun formatDate(date: String): String
    = "${date.substring(8, 10)}-${date.substring(5, 7)}-${date.take(4)}"

@Composable
fun NewsListScreen(viewModel: MainViewModel, navHostController: NavHostController) {
    val newsList = viewModel.newsList.observeAsState()
    when (newsList.value){
        is Resource.Success<*> -> {
            NewsListUi(list = newsList.value!!.data!!)
        }
        is Resource.Loading<*> -> {
            CircularProgressIndicator()
        }
        is Resource.Error<*> -> {
            Text(text = "ERROR")
        }
    }
}

@Composable
fun NewsListUi(list: List<Data>){
    val p = 12.dp
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(p),
        verticalArrangement = Arrangement.spacedBy(p)
    ){
        items(list){ item ->  
            NewsItemUi(data = item)
        }
    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    //derive state for loading more items
    val shouldLoadMore = remember {
        derivedStateOf{
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }
    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore){
        snapshotFlow{shouldLoadMore.value}.collect{
            if (it) loadMore()
        }
    }
}

@Composable
fun NewsItemUi(data: Data){
    val cardHeight = 150.dp
    val cardPadding = 12.dp
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(cardHeight), elevation = 8.dp) {
        Row(modifier = Modifier
            .padding(cardPadding)
            .fillMaxSize()) {
            GlideImage(
                imageModel = data.imageUrl,
                modifier = Modifier
                    .size(cardHeight - cardPadding * 2)
                    .align(Alignment.CenterVertically)
                    .padding(end = cardPadding),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.placeholder_image)
            )
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Column(
                    modifier = Modifier.align(Alignment.BottomStart),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(text = formatDate(data.publishedAt))
                    Text(text = data.source)
                }
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(40.dp),
                ) {

                }
            }
        }
    }
}

@Preview()
@Composable
fun Preview() {
    NewsListUi(previewNewsDataList)
}

val previewNewsData = Data(
    listOf("A" ,"B"),
    "A primeira-ministra de Barbados mostrou-se confiante de que dentro de alguns meses seja possível concretizar \\\"o sonho\\\" de ter ligações aéreas diretas entre ...",
//    "/Users/arenwang/AndroidStudioProjects/NewsAppfinalassignment/app/src/main/res/drawable",
    "https://media-manager.noticiasaominuto.com/1280/naom_6312f6d20c8aa.jpg",
    "",
    "",
    "2022-09-03T07:41:34.000000Z",
    "Análise: Demorámos a adaptar-nos ao jogo do Sporting. Devíamos ter feito um pouco mais daquilo que fizemos na segunda parte, apertar um pouco mais com o Spor...",
    "noticiasaominuto.com",
    "Primeira-ministra de Barbados confiante em ligações diretas para África",
    "",
    "",
)

val previewNewsDataList = listOf<Data>(
    previewNewsData,
    previewNewsData,
    previewNewsData,
    previewNewsData,
    previewNewsData,
)