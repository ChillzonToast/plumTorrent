package com.example.plumtorrent.data.database

import androidx.room.*
import com.example.plumtorrent.models.Torrent
import kotlinx.coroutines.flow.Flow

@Dao
interface TorrentDao {
    @Query("SELECT * FROM torrents ORDER BY dateAdded DESC")
    fun getAllTorrents(): Flow<List<Torrent>>

    @Query("SELECT * FROM torrents WHERE id = :id")
    suspend fun getTorrentById(id: Int): Torrent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTorrent(torrent: Torrent)

    @Update
    suspend fun updateTorrent(torrent: Torrent)

    @Delete
    suspend fun deleteTorrent(torrent: Torrent)

    @Query("DELETE FROM torrents WHERE id = :id")
    suspend fun deleteTorrentById(id: Int)

    @Query("SELECT * FROM torrents WHERE state = :state")
    fun getTorrentsByState(state: String): Flow<List<Torrent>>
}