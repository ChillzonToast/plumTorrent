package com.example.plumtorrent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.example.plumtorrent.data.database.TorrentDatabase
import com.example.plumtorrent.data.repositories.TorrentRepository
import com.example.plumtorrent.ui.screens.home.HomeScreen
import com.example.plumtorrent.ui.theme.PlumTorrentTheme
import com.example.plumtorrent.ui.screens.home.HomeScreenViewModel

class MainActivity : ComponentActivity() {

    private lateinit var database: TorrentDatabase
    private lateinit var repository: TorrentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlumTorrentTheme {
                database = remember { TorrentDatabase.getDatabase(this@MainActivity) }
                repository = remember { TorrentRepository(database.torrentDao()) }
                val viewModel = remember { HomeScreenViewModel(repository) }
                HomeScreen(viewModel = viewModel)
//              AddTorrentScreen()
//                TorrentDetailsScreen()
            }
        }
    }
}