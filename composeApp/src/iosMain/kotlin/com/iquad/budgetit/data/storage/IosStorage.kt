/**
 * This is the iOS-specific implementation of the Storage interface.
 * It uses NSUserDefaults to persist data on iOS devices.
 */
actual class Storage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual fun putDouble(key: String, value: Double) {
        userDefaults.setDouble(value, key)
    }

    actual fun getDouble(key: String, defaultValue: Double): Double {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.doubleForKey(key)
        } else {
            defaultValue
        }
    }
}

/**
 * Platform-specific composable to create Storage instance
 */
@Composable
actual fun rememberStorage(): Storage {
    return remember { Storage() }
}