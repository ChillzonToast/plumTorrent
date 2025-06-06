package com.example.plumtorrent.ui.screens.torrentdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plumtorrent.R
import com.example.plumtorrent.ui.screens.addtorrent.CategoryIconWithDropdown
import com.example.plumtorrent.ui.screens.addtorrent.HorizontalDiv
import com.example.plumtorrent.ui.screens.addtorrent.SectionHeader
import com.example.plumtorrent.ui.screens.home.beige
import com.example.plumtorrent.ui.screens.home.beige_dim
import com.example.plumtorrent.ui.screens.home.bg_dark
import com.example.plumtorrent.ui.screens.home.plum


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorrentDetailsScreen(
    viewModel: TorrentDetailsViewModel = viewModel()
) {
    val tabs = listOf("OPTIONS","STATUS","FILES","TRACKERS","PEERS","PIECES")
    val selectedTab = viewModel.selectedTab.collectAsState().value

    Scaffold(
        containerColor = bg_dark,
        topBar = {
            Column {
                // Main app bar
                TopAppBar(
                    title = {
                        Text(
                            "When.Life.Gives.You.Tangerines.S01.1080p.ENG.ITA.KOR.H264-TheBlackKing",
                            color = beige,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 18.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle back navigation */ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Your back icon
                                contentDescription = "Back",
                                tint = beige
                            )
                        }
                    },
                    actions = {
                        // Start torrent button
                        IconButton(onClick = { /* Start torrent */ }) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "More options",
                                tint = beige_dim
                            )
                        }//Force reannounce
                        IconButton(onClick = { /* Force reannounce */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.repeat_icon),
                                contentDescription = "More options",
                                tint = beige_dim
                            )
                        }
                        // Three dots menu
                        IconButton(onClick = { /* Show more options */ }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = beige_dim
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = bg_dark // Dark background
                    )
                )
                // Tab row
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = bg_dark, // Same dark background
                    contentColor = beige, // Orange for selected
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = beige // Orange indicator
                        )
                    },
                    edgePadding = 0.dp
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


            }
        },
    ) { paddingValues ->
        when (selectedTab) {
            0 -> OptionsTab(Modifier.padding(paddingValues))
            1 -> StatusTab(Modifier.padding(paddingValues))
            2 -> FilesTab(Modifier.padding(paddingValues))
            3 -> TrackersTab(Modifier.padding(paddingValues))
            4 -> PeersTab(Modifier.padding(paddingValues))
            5 -> PiecesTab(Modifier.padding(paddingValues))
        }
    }

}

@Composable
fun OptionsTab(
    modifier: Modifier = Modifier,
    streamSupport: Boolean = true,
    onStreamSupportChange: (Boolean) -> Unit = {},
    sequentialDownload: Boolean = true,
    onSequentialDownloadChange: (Boolean) -> Unit = {},
    downloadFirstLast: Boolean = true,
    onDownloadFirstLastChange: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
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
                text = "Auto (Movie/TV)",
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
            text = "3.2 GB",
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
fun StatusTab(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        // Title and Status
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Scott Pilgrim vs the World (2017) [YTS.MX]",
                style = MaterialTheme.typography.headlineSmall.copy(
                    lineBreak = LineBreak.Simple
                ),
                color = beige
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Paused",
                style = MaterialTheme.typography.bodyLarge,
                color = beige
            )
            Text(
                text = "83%",
                style = MaterialTheme.typography.bodyLarge,
                color = beige
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Custom Progress Bar
        SegmentedProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Download/Upload Speed
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "0.0 KB/s ↓",
                style = MaterialTheme.typography.bodyMedium,
                color = beige
            )
            Text(
                text = "0.0 KB/s ↑",
                style = MaterialTheme.typography.bodyMedium,
                color = beige
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        ){
            // Download Progress
            InfoRow(
                label = "DOWNLOADED",
                value = "0.0 KB/48.5 GB",
                secondaryLabel = "ETA",
                secondaryValue = "∞"
            )


            // Peers
            InfoRow(
                label = "LEECHERS",
                value = "0",
                secondaryLabel = "SEEDERS",
                secondaryValue = "0"
            )

            // Upload Stats
            InfoRow(
                label = "UPLOADED",
                value = "8.5 GB",
                secondaryLabel = "SHARE RATIO",
                secondaryValue = "0.195"
            )

            // Time Stats
            InfoRow(
                label = "ACTIVE TIME",
                value = "6h 41m",
                secondaryLabel = "SEEDING TIME",
                secondaryValue = "43m 41s"
            )

            // Availability and Pieces
            InfoRow(
                label = "AVAILABILITY",
                value = "0.00",
                secondaryLabel = "PIECES",
                secondaryValue = "3082/6,208 (8.0 MB)"
            )
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    secondaryLabel: String,
    secondaryValue: String,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = beige
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = beige
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = secondaryLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = beige
                )
                Text(
                    text = secondaryValue,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = beige
                )
            }

        }

        HorizontalDiv()
    }
}

@Composable
fun SegmentedProgressBar(
    modifier: Modifier = Modifier,
    segments: Int = 50
) {

    Row(
        modifier = modifier
    ) {
        repeat(segments) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = when {
                            (10 <= index + 1 && index + 1 <= 17) || (23 <= index + 1 && index + 1 <= 28) -> plum
                            else -> beige
                        }
                    )
            )
        }
    }
}
@Composable
fun FilesTab(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        TorrentFileList()
    }
}

@Composable
fun TrackersTab(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(bg_dark)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Trackers list
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                for (i in 1..7) {
                    ListItem(
                        headlineContent = {
                            Column {
                                Text(
                                    text = "udp://tracker.example.com:80/announce",
                                    color = beige,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Not Contacted Yet",
                                    color = beige_dim,
                                    fontSize = 12.sp
                                )
                            }
                        },
                        trailingContent = {
                            Text(
                                text = "45s",
                                color = beige_dim,
                                fontSize = 10.sp
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = bg_dark,
                            headlineColor = beige
                        )
                    )
                }
            }

            //Add tracker button
            Button(
                onClick = { /* Handle add tracker */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = beige,
                    contentColor = bg_dark
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add tracker",
                    tint = bg_dark
                )
                Text(text = "Add trackers")
            }
        }
    }
}

@Composable
fun PeersTab(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(bg_dark)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Add Peers button
            Button(
                onClick = { /* Handle add tracker */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = beige,
                    contentColor = bg_dark
                ),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add peers",
                    tint = bg_dark
                )
                Text(text = "Add peers")
            }

            // Trackers list
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                for (i in 1..7) {
                    ListItem(
                        leadingContent = {
                            Text("\uD83C\uDDFA\uD83C\uDDF8")
                        },
                        headlineContent = {
                            Column {
                                Text(
                                    text = "100.42.12.1${i}",
                                    color = beige,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "20% • 732.0 KB/s ↓ 1.2 MB/s ↑",
                                    color = beige_dim,
                                    fontSize = 12.sp
                                )
                            }
                        },
                        trailingContent = {
                            Text(
                                text = "libtorrent 0.13.4",
                                color = beige_dim,
                                fontSize = 10.sp
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = bg_dark,
                            headlineColor = beige
                        )
                    )
                }
            }

        }
    }
}

@Composable
fun PiecesTab(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            InfoRow(
                label = "PIECES",
                value = "3082/6,208",
                secondaryLabel = "PIECE SIZE",
                secondaryValue = "8.0 MB"
            )
            Text(
                text = "FILES",
                style = MaterialTheme.typography.titleMedium,
                color = beige
            )
            FilesPiecesList()

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilesPiecesList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(bg_dark),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
         items(10) { index ->
            Column {
                Text(
                    text = "When.Life.Gives.You.Tangerines.S01E0${index+1}.1080p.ENG.ITA.KOR.H264.mkv",
                    color = beige,
                    fontSize = 14.sp
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(23) { index ->
                        Box(
                            modifier = Modifier
                                .size(15.dp)
                                .background(beige)
                        )
                    }
                    repeat(12) { index ->
                        Box(
                            modifier = Modifier
                                .alpha(0.2f)
                                .size(15.dp)
                                .background(beige)
                        )
                    }
                }
            }
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
        items(12) { index ->
            TorrentFileItem(
                fileName = "When.Life.Gives.You.Tangerines.S01E0${index+1}.1080p.ENG.ITA.KOR.H264.mkv",
                fileSize = "${2.31 * (index+1)} GB",
                streamStatus = "Ready to stream",
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
    streamStatus: String = "Ready to stream",
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
            Row {
                Text(
                    text = fileSize + " • ",
                    color = beige_dim,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = streamStatus,
                    color = beige,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

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

@Preview
@Composable
fun TorrentDetailsScreenPreview() {
    TorrentDetailsScreen()
}