package com.example.plumtorrent.ui.components.torrentDetailsDialogues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.plumtorrent.ui.screens.home.beige
import com.example.plumtorrent.ui.screens.home.beige_dim
import com.example.plumtorrent.ui.screens.home.bg_dark
import com.example.plumtorrent.ui.screens.home.plum
import com.example.plumtorrent.ui.screens.torrentdetails.TorrentDetailsViewModel


@Composable
fun ForceRecheckDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = plum
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Title
                    Text(
                        text = "Force recheck torrent?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = beige,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Text(
                        text = "Partially downloaded pieces will be lost",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = beige_dim,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Buttons Row
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cancel Button
                        TextButton(
                            onClick = {
                                onDismiss()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = beige
                            )
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // OK Button
                        TextButton(
                            onClick = {
                                onConfirm()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = beige
                            )
                        ) {
                            Text(
                                text = "OK",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddTrackersDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var trackersInput by remember { mutableStateOf("") }

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = plum
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Title
                    Text(
                        text = "Enter the trackers url..",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = beige,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Text Field
                    OutlinedTextField(
                        value = trackersInput,
                        onValueChange = { trackersInput = it },
                        label = {
                            Text(
                                "trackers",
                                color = beige.copy(alpha = 0.7f)
                            )
                        },
                        placeholder = {
                            Text(
                                "Use commas for multiple trackers..",
                                color = beige.copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = beige,
                            unfocusedTextColor = beige,
                            focusedBorderColor = beige,
                            unfocusedBorderColor = beige.copy(alpha = 0.5f),
                            cursorColor = beige
                        ),
                        singleLine = false,
                        maxLines = 3
                    )

                    // Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cancel Button
                        TextButton(
                            onClick = {
                                trackersInput = ""
                                onDismiss()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = beige
                            )
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // OK Button
                        TextButton(
                            onClick = {
                                onConfirm(trackersInput)
                                trackersInput = ""
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = beige
                            )
                        ) {
                            Text(
                                text = "OK",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun AddPeersDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var peersInput by remember { mutableStateOf("") }

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = plum
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Title
                    Text(
                        text = "Enter the peers to add",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = beige,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Text Field
                    OutlinedTextField(
                        value = peersInput,
                        onValueChange = { peersInput = it },
                        label = {
                            Text(
                                "peers",
                                color = beige.copy(alpha = 0.7f)
                            )
                        },
                        placeholder = {
                            Text(
                                "Use commas for multiple peers..",
                                color = beige.copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = beige,
                            unfocusedTextColor = beige,
                            focusedBorderColor = beige,
                            unfocusedBorderColor = beige.copy(alpha = 0.5f),
                            cursorColor = beige
                        ),
                        singleLine = false,
                        maxLines = 3
                    )

                    // Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cancel Button
                        TextButton(
                            onClick = {
                                peersInput = ""
                                onDismiss()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = beige
                            )
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // OK Button
                        TextButton(
                            onClick = {
                                onConfirm(peersInput)
                                peersInput = ""
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = beige
                            )
                        ) {
                            Text(
                                text = "OK",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun TorrentSettingsDialogue(
    showDialog: Boolean,
    viewModel: TorrentDetailsViewModel,
    onDismiss: () -> Unit,
) {

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = plum
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Title
                    Text(
                        text = "Torrent Settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = beige,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    //Max Upload Speed
                    TextButton(
                        onClick = {
                            viewModel.toggleMaxSpeedDialog(isUpload = true)
                            onDismiss()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = beige
                        )
                    ) {
                        Text(
                            text = "Max Upload Speed",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    //Max Download Speed
                    TextButton(
                        onClick = {
                            viewModel.toggleMaxSpeedDialog(isUpload = false)
                            onDismiss()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = beige
                        )
                    ) {
                        Text(
                            text = "Max Downlaod Speed",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MaxSpeedDialogue(
    showDialog: Boolean,
    isUpload: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var speedInput by remember { mutableStateOf("") }

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = plum
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Title
                    Text(
                        text = if (isUpload==true) {"Max Upload Speed"} else {"Max Download Speed"},
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = beige,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Text Field
                    OutlinedTextField(
                        value = speedInput,
                        onValueChange = { speedInput = it },
                        label = {
                            Text(
                                text = if (isUpload==true) {"Upload"} else {"Download"},
                                color = beige.copy(alpha = 0.7f)
                            )
                        },
                        placeholder = {
                            Text(
                                "Max Speed in KB/s",
                                color = beige.copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = beige,
                            unfocusedTextColor = beige,
                            focusedBorderColor = beige,
                            unfocusedBorderColor = beige.copy(alpha = 0.5f),
                            cursorColor = beige
                        ),
                        singleLine = false,
                        maxLines = 3
                    )

                    // Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cancel Button
                        TextButton(
                            onClick = {
                                speedInput = ""
                                onDismiss()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = beige
                            )
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // OK Button
                        TextButton(
                            onClick = {
                                onConfirm(speedInput)
                                speedInput = ""
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = beige
                            )
                        ) {
                            Text(
                                text = "OK",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TOrrentDetailsMenu(
    viewModel: TorrentDetailsViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = beige_dim
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = bg_dark,
        ) {
            DropdownMenuItem(
                text = { Text("Force Reannounce", color = beige) },
                onClick = {
                    { /* Handle force reannounce action */ }
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Share magnet link", color = beige) },
                onClick = {
                    { /* Handle share magnet link action */ }
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Save torrent file", color = beige) },
                onClick = {
                    { /* Handle  action */ }
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Remove torrent", color = beige) },
                onClick = {
                    { /* Handle  action */ }
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Torrent settings", color = beige) },
                onClick = {
                    viewModel.toggleTorrentSettingsDialog()
                    expanded = false
                }
            )
        }
    }
}