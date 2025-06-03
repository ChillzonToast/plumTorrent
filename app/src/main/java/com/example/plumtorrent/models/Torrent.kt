package com.example.plumtorrent.models

import java.util.Date

data class Torrent(
    val id: Int, // Unique identifier for the torrent
    val hash: String,
    val name: String,
    val magnetUri: String,
    val size: Long,
    var category: String? = null,
    var downloadedBytes: Long = 0L,
    var uploadedBytes: Long = 0L,
    var downloadSpeed: Long = 0L,
    var uploadSpeed: Long = 0L,
    var state: TorrentState,
    var progress: Float,
    var eta: Long? = null,
    var seeders: Int = 0,
    var leechers: Int = 0,
    var peers: List<Peer> = emptyList(),
    var ratio: Float = 0f,
    val dateAdded: Date,
    var dateCompleted: Date? = null,
    val downloadPath: String,
    var files: List<TorrentFile> = emptyList(),
    val isPrivate: Boolean = false,
    var comment: String = "",
    var creator: String = ""
)

enum class TorrentState {
    DOWNLOADING,        // Currently downloading
    SEEDING,           // Download complete, uploading to others
    PAUSED,            // Manually paused
    CHECKING,          // Verifying files
    ERROR,             // Something went wrong
    COMPLETED         // Download finished
}

// TorrentFile.kt
data class TorrentFile(
    val name: String,               // File name
    val path: String,               // Full path within torrent
    val size: Long,                 // File size in bytes
    var downloadedBytes: Long = 0L, // How much of this file downloaded
    var isComplete: Boolean = false // Completion status can change
)

// Peer.kt
data class Peer(
    val ip: String,                 // IP address of the peer
    var percent: Int = 0,           // Download progress percentage
    var downloadSpeed: Long,        // Changes over time
    var uploadSpeed: Long,          // Changes over time
    var clientName: String? = null  // Can change if detected later
)