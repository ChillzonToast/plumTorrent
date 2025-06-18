package com.example.plumtorrent.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plumtorrent.data.repositories.TorrentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.plumtorrent.models.Torrent
import com.example.plumtorrent.models.TorrentState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class HomeScreenViewModel(private val repository: TorrentRepository) : ViewModel() {

    private val _torrents = MutableStateFlow<List<Torrent>>(emptyList())
    val torrents: StateFlow<List<Torrent>> = _torrents.asStateFlow()

    private val _category = MutableStateFlow<String?>(null)
    val category: StateFlow<String?> = _category.asStateFlow()

    private val _homeTab = MutableStateFlow<Int>(0)
    val homeTab: StateFlow<Int> = _homeTab.asStateFlow()

    private val _sortBy = MutableStateFlow("id")
    val sortBy: StateFlow<String> = _sortBy.asStateFlow()

    private val _sortDirection = MutableStateFlow("asc")
    val sortDirection: StateFlow<String> = _sortDirection.asStateFlow()

    // Filtered and sorted torrents based on current filters
    val filteredTorrents: StateFlow<List<Torrent>> = combine(
        torrents,
        category,
        homeTab,
        sortBy,
        sortDirection
    ) { torrents, category, tab, sort, direction ->
        // Filter by tab
        val tabFilteredTorrents = when (tab) {
            0 -> torrents // All
            1 -> torrents.filter { it.state != TorrentState.COMPLETED } // Downloading
            2 -> torrents.filter { it.state == TorrentState.COMPLETED } // Completed
            else -> emptyList()
        }

        // Filter by category
        val categoryFilteredTorrents = if (category == null) {
            tabFilteredTorrents
        } else {
            tabFilteredTorrents.filter { it.category == category }
        }

        // Sort torrents
        val sortedTorrents = when (sort) {
            "id" -> categoryFilteredTorrents.sortedBy { it.id }
            "name" -> categoryFilteredTorrents.sortedBy { it.name.lowercase() }
            "dateAdded" -> categoryFilteredTorrents.sortedBy { it.dateAdded }
            "dateCompleted" -> categoryFilteredTorrents.sortedBy { it.dateCompleted ?: Date(0) }
            "downloadSpeed" -> categoryFilteredTorrents.sortedBy { it.downloadSpeed }
            "uploadSpeed" -> categoryFilteredTorrents.sortedBy { it.uploadSpeed }
            "eta" -> categoryFilteredTorrents.sortedBy { it.eta ?: Long.MAX_VALUE }
            "size" -> categoryFilteredTorrents.sortedBy { it.size }
            "progress" -> categoryFilteredTorrents.sortedBy { it.progress }
            else -> categoryFilteredTorrents
        }

        // Apply sort direction
        if (direction == "desc") {
            sortedTorrents.reversed()
        } else {
            sortedTorrents
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            repository.getAllTorrents().collect { torrentList ->
                _torrents.value = torrentList
            }
        }
    }

    fun addTorrent(torrent: Torrent) {
        viewModelScope.launch {
            repository.insertTorrent(torrent)
        }
    }

    fun deleteTorrent(torrent: Torrent) {
        viewModelScope.launch {
            repository.deleteTorrent(torrent)
        }
    }

    fun updateTorrent(torrent: Torrent) {
        viewModelScope.launch {
            repository.updateTorrent(torrent)
        }
    }
    fun insertSampleData() {
        viewModelScope.launch {
            repository.insertSampleData()
        }
    }

    fun setCategory(category: String?) {
        _category.value = category
    }

    fun selectTab(tabIndex: Int) {
        _homeTab.value = tabIndex
    }

    fun setSortBy(sort: String) {
        _sortBy.value = sort
    }

    fun setSortDirection(direction: String) {
        _sortDirection.value = direction
    }
    // Add more logic as needed (e.g., add torrent, filter, etc.)
}