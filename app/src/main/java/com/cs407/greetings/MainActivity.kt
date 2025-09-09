package com.cs407.greetings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cs407.greetings.ui.theme.GreetingsTheme

class MainActivity : ComponentActivity() {
    // declare a constant username to parametrize the name
    private val username = "Sandeep"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = username, // parametrized name
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to CS407, $name!", // Update the text
        fontSize = 24.sp, // Adjust font size
        fontWeight = FontWeight.Bold, // Make text bold
        fontStyle = FontStyle.Italic, // Make text italic
        modifier = Modifier
            .fillMaxSize() // Take the full screen size
            .wrapContentSize(Alignment.Center) // Center the content
            .background(Color.Yellow) // Change background color
            .padding(24.dp) // Add padding around text
    )
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingsTheme {
        Greeting("Sandeep")
    }
}