/**
 * This is an expect class that defines the contract for platform-specific storage implementations.
 * In Kotlin Multiplatform, 'expect' declarations require actual implementations in each platform-specific
 * source set (androidMain and iosMain).
 */
expect class Storage {
    /**
     * Stores a double value with the given key
     * @param key The key to store the value under
     * @param value The double value to store
     */
    suspend fun putDouble(key: String, value: Double)

    /**
     * Retrieves a double value for the given key
     * @param key The key to retrieve the value for
     * @param defaultValue The value to return if the key doesn't exist
     * @return The stored double value or defaultValue if not found
     */
    suspend fun getDouble(key: String, defaultValue: Double): Double
}