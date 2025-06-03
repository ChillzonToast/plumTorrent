package com.example.plumtorrent.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.plumtorrent.models.Torrent
import com.example.plumtorrent.data.repositories.listTorrents
import com.example.plumtorrent.models.TorrentState
import java.util.Date

class HomeScreenViewModel : ViewModel() {
    private val _torrents = MutableStateFlow(listTorrents())
    val torrents: StateFlow<List<Torrent>> = _torrents

    private val _category = MutableStateFlow<String?>(null)
    val category: StateFlow<String?> = _category

    private val _homeTab = MutableStateFlow<Int>(0)
    val homeTab: StateFlow<Int> = _homeTab

    fun getTorrents(
        sort: String = "id",
        sortDirection: String = "asc"
    ): List<Torrent> {

        val filteredTorrents = when (homeTab.value) {
            0 -> torrents.value.filter { torrent ->
                (category.value == null || torrent.category == category.value)
            }
            1 -> torrents.value.filter { torrent ->
                (category.value == null || torrent.category == category.value) &&
                        (torrent.state != TorrentState.COMPLETED)
            }
            2 -> torrents.value.filter { torrent ->
                (category.value == null || torrent.category == category.value) &&
                        (torrent.state == TorrentState.COMPLETED)
            }
            else -> emptyList()
        }

        val sortedTorrents = when (sort) {
            "id" -> filteredTorrents.sortedBy { it.id }
            "name" -> filteredTorrents.sortedBy { it.name.lowercase() }
            "dateAdded" -> filteredTorrents.sortedBy { it.dateAdded }
            "dateCompleted" -> filteredTorrents.sortedBy { it.dateCompleted ?: Date(0) }
            "downloadSpeed" -> filteredTorrents.sortedBy { it.downloadSpeed }
            "uploadSpeed" -> filteredTorrents.sortedBy { it.uploadSpeed }
            "eta" -> filteredTorrents.sortedBy { it.eta ?: Long.MAX_VALUE }
            "size" -> filteredTorrents.sortedBy { it.size }
            "progress" -> filteredTorrents.sortedBy { it.progress }
            else -> filteredTorrents
        }

        return if (sortDirection == "desc") {
            sortedTorrents.reversed()
        } else {
            sortedTorrents
        }
    }

    fun setCategory(category: String?) {
        _category.value = category
    }

    fun selectTab(tabIndex: Int) {
        _homeTab.value = tabIndex
    }
    // Add more logic as needed (e.g., add torrent, filter, etc.)
}