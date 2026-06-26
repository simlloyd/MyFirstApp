package com.bosssim.myfirstapp

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.overscroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.bosssim.myfirstapp.ui.theme.MyFirstAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstAppTheme {
                UserListScreen()

            }
        }
    }
}

@Composable
fun UserListScreen() {
    val users = remember { mutableStateListOf<User>() }
    val scope = rememberCoroutineScope()
    val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZhMmU3ZGI4NDQ0MWZjY2RmNDJhNDZhNCIsImlhdCI6MTc4MjQ1MTExMSwiZXhwIjoxNzgyNTM3NTExfQ.ojIlPaSrF21wKFDKB74yR33l4gnZxGbxuWMZdZf10_g"

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    fun fetchUsers() {
        scope.launch {
            try {
                val result = RetrofitInstance.api.getUsers(token)
                users.clear()
                users.addAll(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    LaunchedEffect(Unit) {
        fetchUsers()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Register New User", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        RetrofitInstance.api.registerUser(NewUser(name, email, password, city))
                        name = ""
                        email = ""
                        password = ""
                        city = ""
                        fetchUsers()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register User")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Users List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Name: ${user.name}")
                            Text(text = "City: ${user.city}")
                        }
                        Button(
                            onClick = {
                                scope.launch {
                                    try {
                                        RetrofitInstance.api.deleteUser(token, user._id)
                                        fetchUsers()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
