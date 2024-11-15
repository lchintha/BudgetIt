import SwiftUI
import shared

@main
struct BudgetItApp: App {
    init() {
         KoinIOSKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}