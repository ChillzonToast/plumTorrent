@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.plumtorrent.ui.screens.addtorrent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plumtorrent.ui.screens.home.beige
import com.example.plumtorrent.ui.screens.home.beige_dim
import com.example.plumtorrent.ui.screens.home.bg_dark
import com.example.plumtorrent.ui.screens.home.plum
import com.example.plumtorrent.R

@Composable
fun AddTorrentScreen(
    onBackClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("OPTIONS", "FILES", "TRACKERS")

    var streamSupport by remember { mutableStateOf(true) }
    var sequentialDownload by remember { mutableStateOf(true) }
    var downloadFirstLast by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg_dark)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Add torrent",
                    color = beige,
                    fontSize = 20.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = beige
                    )
                }
            },
            actions = {
                TextButton(onClick = onCancelClick) {
                    Text(
                        "CANCEL",
                        color = beige_dim,
                        fontWeight = FontWeight.Medium
                    )
                }
                IconButton(onClick = onAddClick) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add",
                        tint = beige_dim
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = bg_dark
            )
        )

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = bg_dark,
            contentColor = beige_dim,
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
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Medium else FontWeight.Normal,
                            color = if (selectedTab == index) beige else beige_dim
                        )
                    }
                )
            }
        }

        // Content
        when (selectedTab) {
            0 -> OptionsTab(
                streamSupport = streamSupport,
                onStreamSupportChange = {
                    streamSupport = it
                    if (it) {
                        sequentialDownload = true
                        downloadFirstLast = true
                    }
                },
                sequentialDownload = sequentialDownload,
                onSequentialDownloadChange = { sequentialDownload = it },
                downloadFirstLast = downloadFirstLast,
                onDownloadFirstLastChange = { downloadFirstLast = it }
            )
            1 -> FilesTab()
            2 -> TrackersTab()
        }
    }
}

@Composable
fun OptionsTab(
    streamSupport: Boolean,
    onStreamSupportChange: (Boolean) -> Unit,
    sequentialDownload: Boolean,
    onSequentialDownloadChange: (Boolean) -> Unit,
    downloadFirstLast: Boolean,
    onDownloadFirstLastChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Name Section
        SectionHeader("NAME")
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "When.Life.Gives.You.Tangerines.S01.1080p.ENG.ITA.KOR.H264-TheBlackKing",
                modifier = Modifier.weight(1f),
                color = beige_dim,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit name",
                    tint = beige_dim
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDiv()
        Spacer(modifier = Modifier.height(12.dp))

        // Download Path Section
        SectionHeader("DOWNLOAD PATH")
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "/storage/emulated/0/Download/Flud",
                    color = beige_dim,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "44.8 GB free",
                    color = beige_dim,
                    fontSize = 14.sp
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.folder_icon),
                    contentDescription = "Browse folder",
                    tint = beige_dim
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDiv()
        Spacer(modifier = Modifier.height(12.dp))
        // Download Settings Section
        SectionHeader("DOWNLOAD SETTINGS")
        Spacer(modifier = Modifier.height(16.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(bg_dark)
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Stream support",
                    color = beige,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = streamSupport,
                    onCheckedChange = onStreamSupportChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = beige_dim,
                        checkedTrackColor = plum,
                        uncheckedThumbColor = beige,
                        uncheckedTrackColor = bg_dark,
                        uncheckedBorderColor = beige
                    )
                )

            }


            // Sequential Download
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable sequential download",
                    color = beige,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Checkbox(
                    checked = sequentialDownload,
                    onCheckedChange = onSequentialDownloadChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = beige_dim,
                        uncheckedColor = beige_dim,
                        checkmarkColor = bg_dark
                    ),
                    enabled = !streamSupport
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            // Download First and Last
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Download First and last pieces first",
                    color = beige,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Checkbox(
                    checked = downloadFirstLast,
                    onCheckedChange = onDownloadFirstLastChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = beige_dim,
                        uncheckedColor = beige_dim,
                        checkmarkColor = bg_dark
                    ),
                    enabled = !streamSupport
                )
            }
        }


        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDiv()
        Spacer(modifier = Modifier.height(12.dp))

        // Category Section
        SectionHeader("CATEGORY")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Auto (Downloading metadata)",
                color = beige_dim,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            CategoryIconWithDropdown()
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDiv()
        Spacer(modifier = Modifier.height(12.dp))

        // Total Size Section
        SectionHeader("TOTAL SIZE")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Downloading metadata",
            color = beige_dim,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDiv()
        Spacer(modifier = Modifier.height(12.dp))

        // Hash Section
        SectionHeader("HASH")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "8c99166afa802bbfe0c7a487768bbf3c20fd\ne37a",
            color = beige_dim,
            fontSize = 14.sp,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun FilesTab() {
    TorrentFileList()
}

@Composable
fun TrackersTab() {
    var trackers by remember { mutableStateOf(listOf("udp://tracker.example.com:80/announce", "http://tracker2.example.com/announce", "http://tracker3.example.com:80/announce")) }

    for (tracker in trackers) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = tracker,
            color = beige,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.1.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = beige,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp
    )
}

@Composable
fun HorizontalDiv() {
    HorizontalDivider(
        color = beige_dim,
        modifier = Modifier
            .alpha(0.5f)
    )
}

@Composable
fun CategoryIconWithDropdown(
    onSelect: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Sort",
                tint = beige
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = bg_dark,
        ) {
            DropdownMenuItem(
                text = { Text("Auto", color = beige) },
                onClick = {
                    onSelect("Auto")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Movie/TV", color = beige) },
                onClick = {
                    onSelect("Movie/TV")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("PC Program", color = beige) },
                onClick = {
                    onSelect("PC Program")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Music", color = beige) },
                onClick = {
                    onSelect("Music")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Other", color = beige) },
                onClick = {
                    onSelect("Other")
                    expanded = false
                }
            )
        }
    }
}
@Composable
private fun TorrentFileList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(bg_dark),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(3) { index ->
            TorrentFileItem(
                fileName = "When.Life.Gives.You.Tangerines.S01E0${index+1}.1080p.ENG.ITA.KOR.H264.mkv",
                fileSize = "${2.31 * (index+1)} GB",
                isSelected = index < 2
            )
        }
    }
}

@Composable
private fun TorrentFileItem(
    fileName: String,
    fileSize: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        // File icon
        Icon(
            painter = painterResource(id = R.drawable.file_icon),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .offset(y = 2.dp),
            tint = beige
        )

        Spacer(modifier = Modifier.width(12.dp))

        // File info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = fileName,
                color = beige,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 18.sp
            )

            Text(
                text = fileSize,
                color = beige_dim,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Checkbox
        Checkbox(
            checked = isSelected,
            onCheckedChange = { },
            colors = CheckboxDefaults.colors(
                checkedColor = beige_dim,
                uncheckedColor = beige_dim,
                checkmarkColor = bg_dark
            ),
            modifier = Modifier.size(20.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun AddTorrentScreenPreview() {
    MaterialTheme {
        AddTorrentScreen()
    }
}