/**
 * The main welcome screen composable. This is the first screen users see when they
 * haven't set a budget yet. It combines all the UI components and handles navigation.
 */
@Composable
fun WelcomeScreen(
    viewModel: WelcomeScreenViewModel,
    onNavigateToHome: () -> Unit
) {
    // Collect UI state
    val state by viewModel.state.collectAsState()

    // Handle navigation
    LaunchedEffect(state.shouldNavigateToHome) {
        if (state.shouldNavigateToHome) {
            onNavigateToHome()
        }
    }

    // Screen content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag("welcome_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Title
        Text(
            text = "Welcome to Budget It",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )

        // Subtitle
        Text(
            text = "Setting a monthly budget helps you track spending\n" +
                   "and reach your financial goals",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Budget input
        BudgetInput(
            value = state.budgetText,
            onValueChange = viewModel::onBudgetChanged,
            isError = state.showError
        )

        Text(
            text = "Enter budget",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Continue button
        Button(
            onClick = viewModel::onContinueClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * Preview composable for the Welcome Screen
 */
@Preview(showBackground = true)
@Composable
private fun WelcomeScreenPreview() {
    BudgetItTheme {
        val previewViewModel = WelcomeScreenViewModel(
            setBudgetUseCase = FakeSetBudgetUseCase(),
            getBudgetStatusUseCase = FakeGetBudgetStatusUseCase()
        )

        WelcomeScreen(
            viewModel = previewViewModel,
            onNavigateToHome = {}
        )
    }
}