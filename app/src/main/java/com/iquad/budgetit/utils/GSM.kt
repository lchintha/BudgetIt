package com.iquad.budgetit.utils

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class MessageType(val icon: ImageVector, val backgroundColor: Color) {
    SUCCESS(Icons.Rounded.CheckCircle, Color(0xFFDFF2BF)),
    FAILURE(Icons.Rounded.Cancel, Color(0xFFFFD2D2)),
    WARNING(Icons.Rounded.Warning, Color(0xFFFFE8A1))
}

object GlobalStaticMessage {
    private val messageState = mutableStateOf<GlobalMessageData?>(null)

    fun show(context: Context, title: String, messageType: MessageType) {
        messageState.value = GlobalMessageData(title, messageType)
    }

    @Composable
    fun Display() {
        val currentMessage by messageState
        AnimatedVisibility(
            visible = currentMessage != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            currentMessage?.let {
                MessageView(
                    title = it.title,
                    messageType = it.messageType,
                    onClose = { messageState.value = null }
                )
            }
        }
    }
}

data class GlobalMessageData(val title: String, val messageType: MessageType)

@Composable
fun MessageView(title: String, messageType: MessageType, onClose: () -> Unit) {
    LaunchedEffect(title) {
        delay(3000) // Auto close after 3 seconds
        onClose()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = messageType.backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = messageType.icon,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
            IconButton(onClick = { onClose() }) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    tint = Color.Black
                )
            }
        }
    }
}
