package com.example.newsappfinalassignment.ui.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsappfinalassignment.ui.theme.NewsAppfinalAssignmentTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(auth: FirebaseAuth) {

    val TAG = "log in screen"
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = email,
                onValueChange = {text -> email = text },
                label = { Text(text = "email")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            TextField(
                value = password,
                onValueChange = {text -> password = text },
                label = { Text(text = "password")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Column(horizontalAlignment = Alignment.End) {
                Button(onClick = {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{
                            if (it.isSuccessful) Log.d(TAG, "user sign in successful")
                            else Log.d(TAG, "user sign in failed", it.exception)
                        }
                }) {
                    Text(text = "Sign in")
                }
                Button(onClick = {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener{
                            if (it.isSuccessful) Log.d(TAG, "user sign up successful")
                            else Log.d(TAG, "user sign up failed", it.exception)
                        }
                }) {
                    Text(text = "Sign up")
                }
            }
        }
//        GoogleButton()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NewsAppfinalAssignmentTheme {
        LoginScreen(Firebase.auth)
    }
}
