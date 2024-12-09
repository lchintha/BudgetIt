package com.iquad.budgetit.settings

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.iquad.budgetit.R
import com.iquad.budgetit.model.AppearanceOption
import com.iquad.budgetit.model.Currency
import com.iquad.budgetit.utils.BudgetItToolBar
import com.iquad.budgetit.utils.CurrencyDropdown
import com.iquad.budgetit.utils.InputAmountTextField

@Composable
fun SettingsScreen(navController: NavController) {
    val selectedCurrency = remember { mutableStateOf(Currency.USD) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BudgetItToolBar(
                navController = navController,
                title = stringResource(R.string.settings)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .weight(1f),
            ) {
                Preferences(
                    selectedCurrency = selectedCurrency
                )
                Spacer(modifier = Modifier.height(16.dp))
                ShareAndSupportSection()
                Spacer(modifier = Modifier.height(16.dp))
                LegalSection()
                Spacer(modifier = Modifier.height(16.dp))
            }
            VersionSection()
        }
    }
}

@Composable
fun Preferences(
    selectedCurrency: MutableState<Currency>
) {
    TitleText(
        title = stringResource(R.string.preferences)
    )
    Spacer(modifier = Modifier.height(8.dp))
    AppearanceSection()
    Spacer(modifier = Modifier.height(8.dp))
    MonthlyBudgetSection(
        selectedCurrency = selectedCurrency
    )
}

@Composable
fun ShareAndSupportSection() {
    Column {
        TitleText(
            title = stringResource(R.string.share_and_support)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ShareAndSupportTab(
            leftIcon = Icons.Rounded.Share,
            title = stringResource(R.string.share_app),
            rightIcon = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            onTabClick = {}
        )
        Spacer(modifier = Modifier.height(4.dp))
        ShareAndSupportTab(
            leftIcon = Icons.Rounded.StarBorder,
            title = stringResource(R.string.rate_app),
            rightIcon = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            onTabClick = {}
        )
        Spacer(modifier = Modifier.height(4.dp))
        ShareAndSupportTab(
            leftIcon = Icons.Rounded.MailOutline,
            title = stringResource(R.string.send_feedback),
            rightIcon = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            onTabClick = {}
        )
    }
}

@Composable
fun LegalSection() {
    TitleText(
        title = stringResource(R.string.legal)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = colorResource(R.color.light_blue)
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
                    .clickable {  }
            )
            Text(
                text = stringResource(R.string.privacy_policy),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(R.color.colorPrimary),
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(start = 5.dp, bottom = 5.dp)
                    .clickable {  }
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
fun AppearanceSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = colorResource(R.color.light_blue)
            )
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            TitleText(
                title = stringResource(R.string.appearance),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppearanceOptionsList()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MonthlyBudgetSection(
    selectedCurrency: MutableState<Currency>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = colorResource(R.color.light_blue)
            )
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            TitleText(
                title = stringResource(R.string.monthly_budget),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black
                )
            )
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyDropdown(
                    selectedCurrency = selectedCurrency
                )
                Spacer(modifier = Modifier.width(8.dp))
                InputAmountTextField(
                    onValueChange = {},
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
fun AppearanceOptionsList() {
    var selectedOption by remember { mutableStateOf(AppearanceOption.Light) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(AppearanceOption.entries.size) { index ->
                val option = AppearanceOption.entries[index]
                AppearanceOptionItem(
                    option = option,
                    isSelected = option == selectedOption,
                    onOptionClick = {
                        selectedOption = option
                    }
                )
            }
        }
    }
}

@Composable
fun AppearanceOptionItem(
    option: AppearanceOption,
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
                color = colorResource(R.color.light_blue)
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
                    color = Color.Black
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

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}