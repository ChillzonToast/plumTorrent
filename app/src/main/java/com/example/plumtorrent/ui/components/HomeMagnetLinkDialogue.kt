package com.example.plumtorrent.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.plumtorrent.ui.screens.home.beige
import com.example.plumtorrent.ui.screens.home.plum

@Composable
fun MagnetLinkDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var magnetLink by remember { mutableStateOf("") }

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
                    containerColor = plum // Reddish-brown color from the image
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
                        text = "Enter the magnet link",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = beige,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Text Field
                    OutlinedTextField(
                        value = magnetLink,
                        onValueChange = { magnetLink = it },
                        label = {
                            Text(
                                "magnet",
                                color = beige.copy(alpha = 0.7f)
                            )
                        },
                        placeholder = {
                            Text(
                                "Paste your magnet link here.",
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
                                magnetLink = ""
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
                                onConfirm(magnetLink)
                                magnetLink = ""
                                onDismiss()
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