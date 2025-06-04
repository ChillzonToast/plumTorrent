package com.example.plumtorrent.data.repositories

import com.example.plumtorrent.models.Torrent
import com.example.plumtorrent.models.TorrentState
import java.util.Date

fun sampleTorrents(): List<Torrent> = List(15) { i ->
    Torrent(
        id = i + 1, // Unique ID for each torrent
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
        creator = "Creator $i",
        trackers = listOf("udp://tracker.example.com:80/announce", "http://tracker2.example.com/announce")
    )
}

fun listTorrents(): List<Torrent> {
    // This function would typically fetch torrents from a database or network source.
    // For now, we return sample data.
    return sampleTorrents()
}
