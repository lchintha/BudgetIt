/**
 * This file provides a common interface for creating platform-specific Storage instances
 * in a Compose-friendly way. The actual implementations are provided in the platform-specific
 * source sets.
 */
@Composable
expect fun rememberStorage(): Storage