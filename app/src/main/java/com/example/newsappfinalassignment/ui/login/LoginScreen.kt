package com.example.newsappfinalassignment.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsappfinalassignment.ui.theme.NewsAppfinalAssignmentTheme

@Composable
fun LoginScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column() {
            TextField(
                value = email,
                onValueChange = {text -> email = text },
                label = { Text(text = "email")}
            )
            TextField(
                value = password,
                onValueChange = {text -> password = text },
                label = { Text(text = "password")}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NewsAppfinalAssignmentTheme {
        LoginScreen()
    }
}
