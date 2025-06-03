package com.example.plumtorrent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.plumtorrent.ui.theme.PlumTorrentTheme
import com.example.plumtorrent.ui.screens.home.HomeScreen
import com.example.plumtorrent.ui.screens.addtorrent.AddTorrentScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlumTorrentTheme {
//              HomeScreen()
                AddTorrentScreen()
            }
        }
    }
}