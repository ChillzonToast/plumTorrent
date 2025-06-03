package com.example.plumtorrent.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.example.plumtorrent.R
import com.example.plumtorrent.models.Torrent
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.plumtorrent.ui.components.TorrentCard
import com.example.plumtorrent.ui.components.FAB
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

val beige = Color(0xFFE4C59E)
val beige_dim = Color(0xFFB2816C)
val bg_dark = Color(0xFF332C2A)
val plum = Color(0xFF803D3B)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel()
) {
    val tabs = listOf("ALL", "QUEUED", "FINISHED")
    var selectedTab = viewModel.homeTab.collectAsState().value
    var selectedCategory = viewModel.category.collectAsState().value

    var isFABClicked by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0
        }
    }

    Scaffold(
        containerColor = bg_dark,
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
                                painter = painterResource(id = R.drawable.sort_icon),
                                contentDescription = "Sort",
                                tint = beige
                            )
                        }
                        // Three dots menu
                        IconButton(onClick = { /* Show more options */ }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = beige
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = bg_dark // Dark background
                    )
                )

                // Tab row
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = bg_dark, // Same dark background
                    contentColor = beige, // Orange for selected
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = beige // Orange indicator
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { viewModel.selectTab(index) },
                            text = {
                                Text(
                                    text = title,
                                    color = if (selectedTab == index) {
                                        beige // Orange when selected
                                    } else {
                                        beige_dim // Gray when not selected
                                    }
                                )
                            }
                        )
                    }
                }

                ChipRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    viewModel = viewModel,
                    selectedChip = selectedCategory
                )
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(
                        bottom = 32.dp,
                        end = 8.dp
                    ),
                contentAlignment = Alignment.BottomEnd
            ) {
                FAB(isScrolled, isFABClicked) {
                    isFABClicked = !isFABClicked
                }
            }
        }
    ) { paddingValues ->
        TorrentsListContent(viewModel.getTorrents(), Modifier.padding(paddingValues), listState = listState)
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
            .background(bg_dark)
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
fun ChipRow(
    modifier: Modifier = Modifier,
    selectedChip: String? = null,
    viewModel: HomeScreenViewModel = viewModel()
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(bg_dark)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(listOf("Movie/TV", "PC Program", "Music", "Other")) { chipText ->

            FilterChip(
                selected = chipText == selectedChip,
                onClick = {
                    if (chipText == selectedChip) {
                        viewModel.setCategory(null) // Deselect if already selected
                    } else {
                        viewModel.setCategory(chipText) // Set selected category
                    }
                },
                label = {
                    Text(
                        text = chipText,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(horizontal = 1.dp, vertical = 0.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.Transparent,
                    selectedContainerColor = Color.Transparent,
                    labelColor = beige_dim,
                    selectedLabelColor = beige
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = beige_dim,
                    selectedBorderColor = beige,
                    borderWidth = 1.2.dp,
                    selectedBorderWidth = 1.2.dp,
                    enabled = true,
                    selected = chipText == selectedChip
                ),
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    FAB(false,true) {}
}

