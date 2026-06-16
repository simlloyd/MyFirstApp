package com.bosssim.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZhMmU3ZGI4NDQ0MWZjY2RmNDJhNDZhNCIsImlhdCI6MTc4MTU4MDg2MCwiZXhwIjoxNzgxNjY3MjYwfQ.ffage5YAwNhbbqaRajK14MkaOkxBwVZFAUnVd8Pff40"

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val  result = RetrofitInstance.api.getUsers(token)
                users.addAll(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Name: ${user.name}")
                        Text(text = "City: ${user.city}")
                    }
                }
            }
        }
    }
}
