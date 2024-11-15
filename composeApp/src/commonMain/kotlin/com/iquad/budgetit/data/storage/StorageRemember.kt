package com.iquad.budgetit.data.storage

import androidx.compose.runtime.Composable

/**
 * This file provides a common interface for creating platform-specific com.iquad.budgetit.data.storage.Storage instances
 * in a Compose-friendly way. The actual implementations are provided in the platform-specific
 * source sets.
 */
@Composable
expect fun rememberStorage(): Storage