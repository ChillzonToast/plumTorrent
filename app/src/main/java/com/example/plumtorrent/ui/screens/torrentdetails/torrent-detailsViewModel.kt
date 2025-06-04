package com.example.plumtorrent.ui.screens.torrentdetails

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TorrentDetailsViewModel : ViewModel() {
    private val _selectedTab = MutableStateFlow<Int>(4)
    val selectedTab: StateFlow<Int> = _selectedTab

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }
}