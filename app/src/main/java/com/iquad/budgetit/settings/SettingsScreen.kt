package com.iquad.budgetit.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iquad.budgetit.R
import com.iquad.budgetit.Screen
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.model.ThemeMode
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.CurrencyDropdown
import com.iquad.budgetit.utils.FlexibleAlertDialog
import com.iquad.budgetit.utils.GlobalStaticMessage
import com.iquad.budgetit.utils.InputAmountTextField
import com.iquad.budgetit.utils.MessageType
import com.iquad.budgetit.utils.Util
import com.iquad.budgetit.utils.playStoreUrl
import com.iquad.budgetit.utils.privacyPolicyUrl
import com.iquad.budgetit.utils.tncUrl
import com.iquad.budgetit.viewmodel.BudgetItViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: BudgetItViewModel
) {

    val uiState by viewModel.uiState.observeAsState()
    val budget by viewModel.budgetState.collectAsState()
    val selectedCurrency = remember { mutableStateOf(budget?.currency ?: Currency.USD) }
    val selectedAmount = remember { mutableStateOf(budget?.amount?.toString() ?: "0") }
    val themeMode by viewModel.currentTheme.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val privacyPolicy = stringResource(R.string.privacy_policy)
    val termsAndConditions = stringResource(R.string.terms_and_conditions)

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BudgetItToolBar(
                title = stringResource(R.string.settings),
                onBackPressed = {
                    if (selectedCurrency.value != budget?.currency ||
                        selectedAmount.value != (budget?.amount?.toString() ?: "0")
                    ) {
                        showDialog = true
                    } else {
                        navController.popBackStack()
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
                    .weight(1f),
            ) {
                Preferences(
                    selectedCurrency = selectedCurrency,
                    selectedAmount = selectedAmount,
                    selectedTheme = themeMode,
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(16.dp))
                ShareAndSupportSection()
                Spacer(modifier = Modifier.height(16.dp))
                LegalSection(
                    onTermsAndConditionsClick = {
                        navController.navigate(
                            Screen.WebPage.createRoute(
                                url = tncUrl,
                                title = termsAndConditions
                            )
                        )
                    },
                    onPrivacyPolicyClick = {
                        navController.navigate(
                            Screen.WebPage.createRoute(
                                url = privacyPolicyUrl,
                                title = privacyPolicy
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            VersionSection()
        }
    }

    if (showDialog) {
        FlexibleAlertDialog(
            description = stringResource(R.string.confirm_budget_change_dialog),
            primaryActionText = stringResource(R.string.confirm),
            secondaryActionText = stringResource(R.string.discard),
            onPrimaryAction = {
                viewModel.processBudget(
                    selectedCurrency.value,
                    selectedAmount.value.toDouble()
                )
            },
            onSecondaryAction = {
                navController.popBackStack()
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

    uiState?.let {
        when (uiState) {
            is BudgetItViewModel.UiState.Success -> {
                navController.popBackStack()
                viewModel.resetState()
            }

            BudgetItViewModel.UiState.Error -> {
                GlobalStaticMessage.show(
                    message = "Enter Budget",
                    messageType = MessageType.FAILURE
                )
                viewModel.resetState()
            }

            else -> {}
        }
    }
}

@Composable
fun Preferences(
    selectedCurrency: MutableState<Currency>,
    selectedAmount: MutableState<String>,
    selectedTheme: ThemeMode,
    viewModel: BudgetItViewModel
) {
    TitleText(
        title = stringResource(R.string.preferences)
    )
    Spacer(modifier = Modifier.height(8.dp))
    AppearanceSection(
        selectedTheme = selectedTheme,
        viewModel = viewModel
    )
    Spacer(modifier = Modifier.height(8.dp))
    MonthlyBudgetSection(
        selectedCurrency = selectedCurrency,
        selectedAmount = selectedAmount
    )
}

@Composable
fun ShareAndSupportSection(
    context: Context = LocalContext.current
) {
    Column {
        TitleText(
            title = stringResource(R.string.share_and_support)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ShareAndSupportTab(
            leftIcon = Icons.Rounded.Share,
            title = stringResource(R.string.share_app),
            rightIcon = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            onTabClick = {
                Util.shareApp(context)
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        ShareAndSupportTab(
            leftIcon = Icons.Rounded.StarBorder,
            title = stringResource(R.string.rate_app),
            rightIcon = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            onTabClick = {
                Util.launchUrl(context, playStoreUrl)
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        ShareAndSupportTab(
            leftIcon = Icons.Rounded.MailOutline,
            title = stringResource(R.string.send_feedback),
            rightIcon = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            onTabClick = {
                Util.sendEmail(context)
            }
        )
    }
}

@Composable
fun LegalSection(
    onTermsAndConditionsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    TitleText(
        title = stringResource(R.string.legal)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.terms_and_conditions),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(R.color.colorPrimary),
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        onTermsAndConditionsClick.invoke()
                    }
            )
            Text(
                text = stringResource(R.string.privacy_policy),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(R.color.colorPrimary),
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(start = 5.dp, bottom = 5.dp)
                    .clickable {
                        onPrivacyPolicyClick.invoke()
                    }
            )
        }
    }
}

@Composable
fun VersionSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.version),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Gray,
                fontSize = 10.sp
            )
        )
    }
}

@Composable
fun AppearanceSection(
    selectedTheme: ThemeMode,
    viewModel: BudgetItViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            TitleText(
                title = stringResource(R.string.appearance),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            ThemeModesList(
                selectedTheme = selectedTheme,
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MonthlyBudgetSection(
    selectedCurrency: MutableState<Currency>,
    selectedAmount: MutableState<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            TitleText(
                title = stringResource(R.string.monthly_budget),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyDropdown(
                    selectedCurrency = selectedCurrency,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
                InputAmountTextField(
                    onValueChange = {
                        selectedAmount.value = it
                    },
                    defaultAmount = selectedAmount,
                    displayHint = false,
                    includeCurrencySymbol = false,
                    size = 16,
                    align = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ThemeModesList(
    selectedTheme: ThemeMode,
    viewModel: BudgetItViewModel
) {
    var selectedOption by remember { mutableStateOf(selectedTheme) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ThemeMode.entries.size) { index ->
                val option = ThemeMode.entries[index]
                ThemeModeItem(
                    option = option,
                    isSelected = option == selectedOption,
                    onOptionClick = {
                        viewModel.setTheme(option)
                        selectedOption = option
                    }
                )
            }
        }
    }
}

@Composable
fun ThemeModeItem(
    option: ThemeMode,
    isSelected: Boolean,
    onOptionClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = if (isSelected) colorResource(R.color.colorPrimary) else Color.LightGray
            )
            .clickable { onOptionClick.invoke() }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = option.name,
            style = if (isSelected)
                MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            else MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Normal
            ),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun ShareAndSupportTab(
    leftIcon: ImageVector,
    title: String,
    rightIcon: ImageVector,
    onTabClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .clickable {
                onTabClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = leftIcon,
                contentDescription = "Share",
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp),
                tint = colorResource(R.color.colorPrimary)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .weight(1f)
            )
            Icon(
                imageVector = rightIcon,
                contentDescription = "Support",
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun TitleText(
    title: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color.Gray
    )
) {
    Text(
        text = title,
        style = style
    )
}
