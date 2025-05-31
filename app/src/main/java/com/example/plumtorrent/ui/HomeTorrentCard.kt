package com.example.plumtorrent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plumtorrent.models.Torrent
import com.example.plumtorrent.models.TorrentState
import com.example.plumtorrent.R

@Composable
fun TorrentCard(
    torrent: Torrent,
    onTorrentClick: (Torrent) -> Unit = {},
    onPlayPauseClick: (Torrent) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTorrentClick(torrent) }
            .background(Color(bg_dark))
        ,
        shape = RoundedCornerShape(8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(bg_dark))
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left content (torrent info)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Torrent name
                Text(
                    text = torrent.name,
                    color = Color(beige),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Progress bar
                LinearProgressIndicator(
                    progress = { torrent.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = Color(beige_dim), // Orange progress
                    trackColor = Color(plum) // Dark track
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Status and progress
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${torrent.category}",
                        color = Color(beige_dim), // Your orange color
                        fontSize = 12.sp
                    )
                    Text(
                        text = " • ",
                        color = Color(beige_dim),
                        fontSize = 12.sp
                    )
                    Text(
                        text = getStatusText(torrent.state),
                        color = Color(beige_dim), // Your orange color
                        fontSize = 12.sp
                    )
                    Text(
                        text = " • ",
                        color = Color(beige_dim),
                        fontSize = 12.sp
                    )
                    Text(
                        text = "${String.format("%.1f", torrent.progress * 100)}%",
                        color = Color(beige_dim),
                        fontSize = 12.sp
                    )
                }

                // Size and speed info
                Row {
                    Text(
                        text = "${formatBytes(torrent.downloadedBytes)} / ${formatBytes(torrent.size)}",
                        color = Color(beige_dim),
                        fontSize = 12.sp
                    )
                    Text(
                        text = " • ",
                        color = Color(beige_dim),
                        fontSize = 12.sp
                    )
                    Text(
                        text = buildSpeedText(torrent),
                        color = Color(beige),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Right side - Play/Pause button
            IconButton(
                onClick = { onPlayPauseClick(torrent) },
                modifier = Modifier
                    .size(48.dp)
                    .then(
                        if (torrent.state != TorrentState.PAUSED)
                            Modifier.border(3.dp, Color(beige), CircleShape)
                        else
                            Modifier
                            .background(
                                color = Color(plum),
                                shape = CircleShape
                            )
                    )
            ) {
                if (torrent.state == TorrentState.PAUSED) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Resume",
                        tint = Color(beige),
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.pause),
                        contentDescription = "Pause",
                        tint = Color(beige),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

// Helper functions
private fun getStatusText(state: TorrentState): String {
    return when (state) {
        TorrentState.DOWNLOADING -> "Downloading"
        TorrentState.SEEDING -> "Seeding"
        TorrentState.PAUSED -> "Paused"
        TorrentState.COMPLETED -> "Completed"
        TorrentState.CHECKING -> "Checking"
        TorrentState.ERROR -> "Error"
    }
}

private fun buildSpeedText(torrent: Torrent): String {
    val downSpeed = if (torrent.downloadSpeed > 0) formatBytes(torrent.downloadSpeed) else "0.0 KB"
    val upSpeed = if (torrent.uploadSpeed > 0) formatBytes(torrent.uploadSpeed) else "0.0 KB"

    return "$downSpeed/s ↓ $upSpeed/s ↑"
}

// Helper function for formatting bytes (add this if not already in your models)
private fun formatBytes(bytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var size = bytes.toDouble()
    var unitIndex = 0

    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }

    return "%.1f %s".format(size, units[unitIndex])
}