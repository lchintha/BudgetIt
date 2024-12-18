package com.iquad.budgetit.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.iquad.budgetit.R

@Composable
fun FlexibleAlertDialog(
    title: String = "",
    description: String,
    primaryActionText: String,
    onPrimaryAction: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryActionText: String? = null,
    onSecondaryAction: (() -> Unit)? = null,
    primaryActionColor: Color = colorResource(R.color.colorPrimary),
    secondaryActionColor: Color = Color.Red,
    onDismiss: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black.copy(alpha = 0.5f)
            )
    ) {
        AlertDialog(
            modifier = modifier,
            title = {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onDismissRequest = onDismiss,
            dismissButton = secondaryActionText?.let {
                {
                    TextButton(
                        onClick = {
                            onSecondaryAction?.invoke()
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = it,
                            color = secondaryActionColor
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onPrimaryAction.invoke()
                        onDismiss()
                    }
                ) {
                    Text(
                        text = primaryActionText,
                        color = primaryActionColor
                    )
                }
            }
        )
    }
}

// Example Usage
@Preview
@Composable
fun AlertDialogExample() {
    FlexibleAlertDialog(
        description = "Are you sure you want to proceed?",
        primaryActionText = "Confirm",
        secondaryActionText = "Discard",
        onPrimaryAction = {
            // Perform primary action
            println("Primary action confirmed")
        },
        onSecondaryAction = {
            // Optional secondary action
            println("Secondary action selected")
        },
        onDismiss = {

        }
    )
}