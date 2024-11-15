import SwiftUI
import shared

@main
struct BudgetItApp: App {
    init() {
         KoinIosKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}