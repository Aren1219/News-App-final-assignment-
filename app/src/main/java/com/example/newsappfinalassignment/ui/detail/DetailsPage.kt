package com.example.newsappfinalassignment.ui.detail

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsappfinalassignment.R
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.ui.MainViewModel
import com.example.newsappfinalassignment.ui.theme.NewsAppfinalAssignmentTheme
import com.example.newsappfinalassignment.util.Screen
import com.example.newsappfinalassignment.util.Util
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailsPage(
    uuid: String,
    viewModel: MainViewModel,
    navHostController: NavHostController,
) {
    val newsData by remember { mutableStateOf(viewModel.getNewsUUID(uuid)) }
    if (newsData != null)
        Column() {
            Top(title = newsData!!.title) { navHostController.navigate(Screen.NewsList.route) }
            Column(modifier = Modifier.padding(12.dp)) {
                MoreDerails(data = newsData!!)
            }
        }
}

@Composable
fun Top(title: String, back: () -> Unit) {
    TopAppBar() {
        IconButton(onClick = { back() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
        }
        Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun MoreDerails (data: Data){
    val cardPadding = 12.dp
    Card(modifier = Modifier.fillMaxSize(), elevation = 8.dp) {
        Column(
            modifier = Modifier
                .padding(cardPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            GlideImage(
                imageModel = data.imageUrl,
                modifier = Modifier.height(250.dp),
                contentScale = ContentScale.Fit,
                error = ImageBitmap.imageResource(id = R.drawable.placeholder_image),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.placeholder_image),
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = data.title, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = Util.formatDate(data.publishedAt))
            Text(text = data.source)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = data.description)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = data.snippet)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTop() {
    NewsAppfinalAssignmentTheme() {
        Top("Some Random News") {}
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewDetails(){
    NewsAppfinalAssignmentTheme() {
        Column() {
            MoreDerails(data = Util.previewNewsData())
        }
    }
}