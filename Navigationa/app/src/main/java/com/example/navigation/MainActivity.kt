package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface (
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        MyApp()
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "first"
    ) {
        composable("first") {
            FirstScreen {
                name -> navController.navigate("second/$name")
            }
        }
        composable("second/{name}") {
            val name = it.arguments?.getString("name") ?: "no name"
            SecondScreen(name) {
                navController.navigate("first")
            }
        }
    }
}

@Composable
fun FirstScreen(
    toSecond: (name: String) -> Unit
) {
    val name = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("First Screen", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = name.value, onValueChange = {
            name.value = it
        })
        Button(
            onClick = {
                toSecond(name.value)
            }
        ) {
            Text("Go To Second Screen")
        }
    }
}

@Composable
fun SecondScreen(
    name: String,
    toFirst: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Second Screen $name", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                toFirst(name)
            }
        ) {
            Text("Go To First Screen")
        }
    }
}