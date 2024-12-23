package com.iquad.budgetit.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.iquad.budgetit.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val animationDuration = 500

enum class MessageType(
    val icon: ImageVector,
    val backGroundColor: Color,
    val borderColor: Color,
    val title: Int
) {
    SUCCESS(
        icon = Icons.Outlined.CheckCircle,
        backGroundColor = Color(0xFFDFF2BF),
        borderColor = Color(0xFF1E7E34),
        title = R.string.success
    ),
    FAILURE(
        icon = Icons.Outlined.Cancel,
        backGroundColor = Color(0xFFFDF2F8),
        borderColor = Color(0xFFDC3545),
        title = R.string.error
    )
}

data class GlobalMessageData(
    val title: String,
    val messageType: MessageType
)

object GlobalStaticMessage {
    private val messageState = mutableStateOf<GlobalMessageData?>(null)

    fun show(message: String, messageType: MessageType) {
        messageState.value = GlobalMessageData(message, messageType)
    }

    @Composable
    fun Display() {
        val currentMessage by messageState
        val windowInsets = WindowInsets.statusBars
        val statusBarHeight = with(LocalDensity.current) { windowInsets.getTop(this).toDp() }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(Float.MAX_VALUE)
                .padding(top = statusBarHeight)
                .padding(horizontal = 16.dp)
        ) {
            AnimatedVisibility(
                visible = currentMessage != null,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(animationDuration)
                ) + expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(animationDuration)
                ) + fadeIn(
                    animationSpec = tween(animationDuration)
                )
            ) {
                currentMessage?.let {
                    MessageView(
                        message = it.title,
                        messageType = it.messageType,
                        onClose = { messageState.value = null }
                    )
                }
            }
        }
    }
}

@Composable
fun MessageView(
    message: String,
    messageType: MessageType,
    onClose: () -> Unit
) {
    LaunchedEffect(message) {
        delay(3000) // Auto close after 3 seconds
        onClose.invoke()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                MaterialTheme.shapes.small
            )
            .border(
                width = 1.dp,
                color = messageType.borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = messageType.backGroundColor,
            )
            .padding(12.dp),
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
                    tint = messageType.borderColor,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(messageType.title),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            color =Color.Black,
                            textAlign = TextAlign.Start
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 12.sp,
                            color =Color.Black,
                            textAlign = TextAlign.Start
                        )
                    )
                }
                IconButton(onClick = { onClose() }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint =Color.Black
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MessageViewPreview() {
    MessageView(
        message = "Successfully added to the cart.",
        messageType = MessageType.SUCCESS,
        onClose = {}
    )
}
