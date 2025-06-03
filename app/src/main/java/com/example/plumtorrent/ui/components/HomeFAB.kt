package com.example.plumtorrent.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.plumtorrent.R
import com.example.plumtorrent.ui.screens.home.beige
import com.example.plumtorrent.ui.screens.home.bg_dark
import com.example.plumtorrent.ui.screens.home.plum

@Composable
fun FAB(
    isScrolled: Boolean,
    isClicked: Boolean = false,
    onFabClick: () -> Unit,
    showMagnetDialog: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            // Animated visibility for additional FABs
            AnimatedVisibility(
                visible = isClicked,
                enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300), initialScale = 0.8f),
                exit = fadeOut(animationSpec = tween(300)) + scaleOut(animationSpec = tween(300) , targetScale = 0.8f)
            ) {
                // Torrent file add
                ExtendedFloatingActionButton(
                    onClick = { /* Handle add torrent file */ },
                    containerColor = beige,
                    contentColor = bg_dark,
                    expanded = true,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.file_icon),
                            contentDescription = "Add torrent file",
                            tint = bg_dark
                        )
                    },
                    text = {
                        Text(
                            text = ".torrent",
                            color = bg_dark
                        )
                    }
                )
            }

            // Animated visibility for additional FABs
            AnimatedVisibility(
                visible = isClicked,
                enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300), initialScale = 0.95f),
                exit = fadeOut(animationSpec = tween(300)) + scaleOut(animationSpec = tween(300) , targetScale = 0.95f)
            ) {
                // Magnet link add
                ExtendedFloatingActionButton(
                    onClick = {
                        showMagnetDialog()
                    },
                    containerColor = beige,
                    contentColor = bg_dark,
                    expanded = true,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.magnet_link_icon),
                            contentDescription = "Add magnet link",
                            tint = bg_dark
                        )
                    },
                    text = {
                        Text(
                            text = "magnet",
                            color = bg_dark
                        )
                    }
                )
            }

        }


        // Main FAB with animated rotation
        val rotation by animateFloatAsState(
            targetValue = if (isClicked) 45f else 0f,
            animationSpec = tween(300),
            label = "fab_rotation"
        )

        ExtendedFloatingActionButton(
            onClick = { onFabClick() },
            containerColor = plum,
            contentColor = beige,
            expanded = (!isScrolled && !isClicked),
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = if (isClicked) "Close" else "Add torrent",
                    tint = beige,
                    modifier = Modifier.rotate(rotation)
                )
            },
            text = {
                Text(
                    text = if (isClicked) "Close" else "Add torrent",
                    color = beige
                )
            }
        )
    }
}
