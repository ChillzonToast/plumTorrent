package com.example.plumtorrent.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import com.example.plumtorrent.R
import com.example.plumtorrent.models.Torrent
import com.example.plumtorrent.models.TorrentState
import java.util.Date
import androidx.compose.foundation.lazy.rememberLazyListState

val beige = 0xFFE4C59E
val beige_dim = 0xFFB2816C
val bg_dark = 0xFF332C2A
val plum = 0xFF803D3B

fun sampleTorrents(): List<Torrent> = List(15) { i ->
    Torrent(
        hash = "hash$i",
        name = "Sample Torrent $i",
        magnetUri = "magnet:?xt=urn:btih:hash$i",
        size = 1024L * 1024 * (i + 1),
        category = "Movie/TV",
        downloadedBytes = 512L * 1024 * (i + 1),
        uploadedBytes = 128L * 1024 * (i + 1),
        downloadSpeed = 100L * (i + 1),
        uploadSpeed = 50L * (i + 1),
        state = TorrentState.PAUSED,
        progress = (i + 1) / 7f,
        eta = 1000L * (7 - i),
        seeders = i,
        leechers = 7 - i,
        peers = emptyList(),
        ratio = 0.5f * (i + 1),
        dateAdded = Date(),
        dateCompleted = null,
        downloadPath = "/downloads/sample$i",
        files = emptyList(),
        isPrivate = false,
        comment = "Sample comment $i",
        creator = "Creator $i"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("ALL", "QUEUED", "FINISHED")
    val torrents = remember { sampleTorrents() }

    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0
        }
    }

    Scaffold(
        containerColor = Color(bg_dark),
        topBar = {
            Column {
                // Main app bar
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo), // Your SVG logo
                                contentDescription = "plumTorrent Logo",
                                modifier = Modifier
                                    .height(64.dp)
                            )
                        }
                    },
                    actions = {
                        // Hamburger menu
                        IconButton(onClick = { /* Open drawer/menu */ }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color(beige)
                            )
                        }
                        // Three dots menu
                        IconButton(onClick = { /* Show more options */ }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = Color(beige)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(bg_dark) // Dark background
                    )
                )

                // Tab row
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color(bg_dark), // Same dark background
                    contentColor = Color(beige), // Orange for selected
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(beige) // Orange indicator
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    color = if (selectedTab == index) {
                                        Color(beige) // Orange when selected
                                    } else {
                                        Color(beige_dim) // Gray when not selected
                                    }
                                )
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(
                        bottom = 64.dp,
                        end = 16.dp
                    ),
                contentAlignment = Alignment.BottomEnd
            ) {
                FAB(isScrolled)
            }
        }
    ) { paddingValues ->
        // Your content based on selected tab
        when (selectedTab) {
            0 -> TorrentsListContent(torrents, Modifier.padding(paddingValues),listState = listState)
            1 -> TorrentsListContent(torrents, Modifier.padding(paddingValues), listState = listState)
            2 -> TorrentsListContent(torrents, Modifier.padding(paddingValues), listState = listState)
        }
    }
}

@Composable
fun TorrentsListContent(
    torrents: List<Torrent>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(Color(bg_dark))
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(torrents) { torrent ->
            TorrentCard(
                torrent = torrent,
                onTorrentClick = { /* Navigate to details */ },
                onPlayPauseClick = { /* Handle play/pause */ }
            )
        }
    }
}

@Composable
fun FAB(isScrolled: Boolean) {
    ExtendedFloatingActionButton(
        onClick = { /* Handle add torrent action */ },
        containerColor = Color(plum),
        contentColor = Color(beige),
        expanded = !isScrolled,
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color(beige)
            )
        },
        text = {
            Text(
                text = "Add torrent",
                color = Color(beige)
            )
        }

    )
}