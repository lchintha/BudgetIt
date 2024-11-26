package com.iquad.budgetit.charts

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
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

@Composable
fun HalfCircleProgressBar(
    spentAmount: Float,
    totalAmount: Float,
    modifier: Modifier = Modifier,
) {
    val backgroundColor: Color = colorResource(R.color.light_blue)
    val percentage: Float
    val foregroundColor: Color = if (spentAmount > totalAmount) {
        percentage = 1f
        Color.Red
    } else {
        percentage = spentAmount / totalAmount
        colorResource(R.color.colorPrimary)
    }

    val animatedPercentage by animateFloatAsState(
        targetValue = percentage,
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
                text = "$$spentAmount",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
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
    val spent = 50f
    val total = 100f

    HalfCircleProgressBar(
        spentAmount = spent,
        totalAmount = total
    )
}