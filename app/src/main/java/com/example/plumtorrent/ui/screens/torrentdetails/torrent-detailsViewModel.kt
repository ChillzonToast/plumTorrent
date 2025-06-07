package com.example.plumtorrent.ui.screens.torrentdetails

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TorrentDetailsViewModel : ViewModel() {
    private val _selectedTab = MutableStateFlow<Int>(5)
    val selectedTab: StateFlow<Int> = _selectedTab

    private val _recheckDialogVisible = MutableStateFlow<Boolean>(false)
    val recheckDialogVisible: StateFlow<Boolean> = _recheckDialogVisible

    private val _addTrackersDialogVisible = MutableStateFlow<Boolean>(false)
    val addTrackersDialogVisible: StateFlow<Boolean> = _addTrackersDialogVisible

    private val _addPeersDialogVisible = MutableStateFlow<Boolean>(false)
    val addPeersDialogVisible: StateFlow<Boolean> = _addPeersDialogVisible

    private val _torrentSettingsDialogVisible = MutableStateFlow<Boolean>(false)
    val torrentSettingsDialogVisible: StateFlow<Boolean> = _torrentSettingsDialogVisible


    private val _maxSpeedDialogVisible = MutableStateFlow<Boolean>(false)
    val maxSpeedDialogVisible: StateFlow<Boolean> = _maxSpeedDialogVisible

    private val _maxSpeedIsUpload = MutableStateFlow<Boolean>(false)
    val maxSpeedIsUpload: StateFlow<Boolean> = _maxSpeedIsUpload

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }

    fun toggleRecheckDialog() {
        _recheckDialogVisible.value = !_recheckDialogVisible.value
    }

    fun recheckTorrent() {
        // Logic to recheck the torrent
        // This could involve calling a repository or service method
        // For now, we just toggle the dialog visibility
        _recheckDialogVisible.value = false
    }
    fun toggleAddTrackersDialog() {
        _addTrackersDialogVisible.value = !_addTrackersDialogVisible.value
    }
    fun addTrackers(trackers: String?) {
        // Logic to add trackers to the torrent
        // This could involve calling a repository or service method
        // For now, we just toggle the dialog visibility
        _addTrackersDialogVisible.value = false
    }
    fun toggleAddPeersDialog() {
        _addPeersDialogVisible.value = !_addPeersDialogVisible.value
    }
    fun addPeers(peers: String?) {
        // Logic to add peers to the torrent
        // This could involve calling a repository or service method
        // For now, we just toggle the dialog visibility
        _addPeersDialogVisible.value = false
    }
    fun toggleTorrentSettingsDialog() {
        _torrentSettingsDialogVisible.value = !_torrentSettingsDialogVisible.value
    }
    fun toggleMaxSpeedDialog(isUpload: Boolean?) {
        // Logic to set the maximum speed for the torrent
        // This could involve calling a repository or service method
        // For now, we just toggle the dialog visibility
        if (isUpload != null) {
            _maxSpeedIsUpload.value = isUpload
        }
        _maxSpeedDialogVisible.value = !_maxSpeedDialogVisible.value
    }
    fun setMaxSpeed(speed: String?) {
        // Logic to set the maximum speed for the torrent
        // This could involve calling a repository or service method
        // For now, we just toggle the dialog visibility
    }
}