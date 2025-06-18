package com.example.plumtorrent.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

@Entity(tableName = "torrents")
@TypeConverters(Converters::class)
data class Torrent(
    @PrimaryKey val id: Int, // Unique identifier for the torrent
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
    var creator: String = "",
    var trackers: List<String> = emptyList(),
)

enum class TorrentState {
    DOWNLOADING,        // Currently downloading
    SEEDING,           // Download complete, uploading to others
    PAUSED,            // Manually paused
    CHECKING,          // Verifying files
    ERROR,             // Something went wrong
    COMPLETED         // Download finished
}

// Data classes
data class TorrentFile(
    val name: String,
    val size: String,
    var type: FileType,
    var isSelected: Boolean = false,
    val isFolder: Boolean = false,
    val children: List<TorrentFile> = emptyList()
)

enum class FileType {
    VIDEO, AUDIO, DOCUMENT, IMAGE, ARCHIVE, FOLDER, UNKNOWN
}

// Peer.kt
data class Peer(
    val ip: String,                 // IP address of the peer
    var percent: Int = 0,           // Download progress percentage
    var downloadSpeed: Long,        // Changes over time
    var uploadSpeed: Long,          // Changes over time
    var clientName: String? = null  // Can change if detected later
)
class Converters {
    @TypeConverter
    fun fromTorrentState(state: TorrentState): String = state.name

    @TypeConverter
    fun toTorrentState(state: String): TorrentState = TorrentState.valueOf(state)

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(timestamp: Long?): Date? = timestamp?.let { Date(it) }

    @TypeConverter
    fun fromStringList(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromPeerList(value: List<Peer>): String = Gson().toJson(value)

    @TypeConverter
    fun toPeerList(value: String): List<Peer> {
        val listType = object : TypeToken<List<Peer>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTorrentFileList(value: List<TorrentFile>): String = Gson().toJson(value)

    @TypeConverter
    fun toTorrentFileList(value: String): List<TorrentFile> {
        val listType = object : TypeToken<List<TorrentFile>>() {}.type
        return Gson().fromJson(value, listType)
    }
}