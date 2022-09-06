package com.example.newsappfinalassignment.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsappfinalassignment.ui.theme.NewsAppfinalAssignmentTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(auth: FirebaseAuth, signedIn: () -> Unit, googleSignIn: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(FocusDirection.Down)})
            )
            TextField(
                value = password,
                onValueChange = { text -> password = text },
                label = { Text(text = "password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
                visualTransformation = PasswordVisualTransformation()
            )
            Column(horizontalAlignment = Alignment.End) {
                Button(onClick = {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{
                            if (it.isSuccessful) {
                                Toast.makeText(context, "sign in successful", Toast.LENGTH_SHORT).show()
                                signedIn()
                            }
                            else Toast.makeText(context, "sign in failed", Toast.LENGTH_SHORT).show()
                        }
                }) {
                    Text(text = "Sign in")
                }
                Button(onClick = {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener{
                            if (it.isSuccessful)
                                Toast.makeText(context, "sign up successful", Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(context, "sign up failed", Toast.LENGTH_SHORT).show()
                        }
                }) {
                    Text(text = "Sign up")
                }
            }
        }
        Button(onClick = {googleSignIn()}) {
            Text(text = "Google sign in")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NewsAppfinalAssignmentTheme {
        LoginScreen(Firebase.auth, {}, {})
    }
}
