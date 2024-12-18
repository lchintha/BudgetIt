package com.iquad.budgetit.charts

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iquad.budgetit.R
import com.iquad.budgetit.model.Currency

@Composable
fun HalfCircleProgressBar(
    spentAmount: Double,
    totalAmount: Double,
    modifier: Modifier = Modifier,
    currency: Currency = Currency.USD,
    onClickListener: () -> Unit
) {
    val backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
    val percentage: Double
    val foregroundColor: Color = if (spentAmount > totalAmount) {
        percentage = 1.0
        Color.Red
    } else {
        percentage = spentAmount / totalAmount
        MaterialTheme.colorScheme.primary
    }

    val animatedPercentage by animateFloatAsState(
        targetValue = percentage.toFloat(),
        animationSpec = tween(durationMillis = 1000),
        label = "progress"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(32.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val width = size.width
            val height = size.height
            val thickness = 120f

            val radius = minOf(width / 2f, height)
            val topLeft = Offset(
                x = width / 2f - radius,
                y = 0f
            )
            val size = Size(radius * 2f, radius * 2f)

            // Draw background arc
            drawArc(
                color = backgroundColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(
                    width = thickness,
                    cap = StrokeCap.Round
                )
            )

            // Draw progress arc
            drawArc(
                color = foregroundColor,
                startAngle = 180f,
                sweepAngle = 180f * animatedPercentage,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(
                    width = thickness,
                    cap = StrokeCap.Round
                )
            )
        }

        // Text container at the bottom
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${currency.symbol}$spentAmount",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable {
                        onClickListener.invoke()
                    }
            )
            Text(
                text = "spent this month",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun HalfCircleProgressBarPreview() {
    val spent = 50.0
    val total = 100.0

    HalfCircleProgressBar(
        spentAmount = spent,
        totalAmount = total,
        currency = Currency.USD,
        onClickListener = {}
    )
}