package com.example.plumtorrent.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.plumtorrent.models.Torrent
import com.example.plumtorrent.data.repositories.listTorrents
import com.example.plumtorrent.models.TorrentState

class HomeScreenViewModel : ViewModel() {
    private val _torrents = MutableStateFlow(listTorrents())
    val torrents: StateFlow<List<Torrent>> = _torrents

    private val _category = MutableStateFlow<String?>(null)
    val category: StateFlow<String?> = _category

    private val _homeTab = MutableStateFlow<Int>(0)
    val homeTab: StateFlow<Int> = _homeTab

    fun getTorrents(): List<Torrent> {
        if (homeTab.value == 0) {
            return torrents.value.filter { torrent ->
                (category.value == null || torrent.category == category.value)
            }
        } else if (homeTab.value == 1) {
            return torrents.value.filter { torrent ->
                (category.value == null || torrent.category == category.value) &&
                        (torrent.state != TorrentState.COMPLETED)
            }
        } else if (homeTab.value == 2) {
            return torrents.value.filter { torrent ->
                (category.value == null || torrent.category == category.value) &&
                        (torrent.state == TorrentState.COMPLETED)
            }
        }
        return emptyList()
    }

    fun setCategory(category: String?) {
        _category.value = category
    }

    fun selectTab(tabIndex: Int) {
        _homeTab.value = tabIndex
    }
    // Add more logic as needed (e.g., add torrent, filter, etc.)
}