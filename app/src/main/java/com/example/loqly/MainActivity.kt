package com.example.loqly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.loqly.ui.navigation.LoqlyApp
import com.example.loqly.ui.theme.LoqlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoqlyTheme {
                LoqlyApp()
            }
        }
    }
}
