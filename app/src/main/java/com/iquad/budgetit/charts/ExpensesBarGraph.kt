package com.iquad.budgetit.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iquad.budgetit.R
import com.iquad.budgetit.model.TabItem
import com.iquad.budgetit.storage.Expense
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun ExpensesBarGraph(
    expenses: List<Expense>,
    startDate: String,
    endDate: String,
    tabItem: TabItem,
    modifier: Modifier = Modifier,
    barColor: Color = colorResource(R.color.colorPrimary),
    textColor: Color = Color.Gray
) {
    val start = LocalDate.parse(startDate)
    val end = LocalDate.parse(endDate)

    val groupedExpenses = when (tabItem) {
        TabItem.Weekly -> groupExpensesByDay(expenses, start, end)
        TabItem.Monthly -> groupExpensesByDay(expenses, start, end)
        TabItem.Yearly -> groupExpensesByMonth(expenses, start, end)
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val padding = 40.dp.toPx()
        val bottomPadding = 32.dp.toPx()
        val topPadding = 20.dp.toPx()
        val graphHeight = canvasHeight - bottomPadding - topPadding
        val graphWidth = canvasWidth - 2 * padding

        val maxAmount = (groupedExpenses.values.maxOrNull() ?: 0.0).coerceAtLeast(1.0)
        val barWidth = graphWidth / groupedExpenses.size * 0.8f

        // Calculate scale factor based on available graph height
        val scaleFactor = graphHeight / maxAmount

        // Calculate baseline Y position
        val baselineY = canvasHeight - bottomPadding

        // Draw Y-axis labels and grid lines
        val yAxisLabelCount = 5
        for (i in 0 until yAxisLabelCount) {
            val yValue = (maxAmount * i / (yAxisLabelCount - 1)).roundToInt()
            val yPos = baselineY - (yValue * scaleFactor)

            // Draw grid line
            drawLine(
                color = Color.LightGray.copy(alpha = 0.3f),
                start = Offset(padding, yPos.toFloat()),
                end = Offset(canvasWidth - padding, yPos.toFloat())
            )

            // Draw Y-axis label
            drawText(
                textMeasurer = textMeasurer,
                text = yValue.toString(),
                topLeft = Offset(10.dp.toPx(), (yPos - 5.dp.toPx()).toFloat()),
                style = TextStyle(
                    color = textColor,
                    fontSize = 10.sp
                )
            )
        }

        // Draw baseline
        drawLine(
            color = Color.LightGray,
            start = Offset(padding, baselineY),
            end = Offset(canvasWidth - padding, baselineY)
        )

        // Draw bars and labels
        groupedExpenses.entries.forEachIndexed { index, (label, amount) ->
            val barHeight = (amount * scaleFactor).toFloat()
            val left = padding + index * (graphWidth / groupedExpenses.size)

            // Draw bar starting from baseline
            drawRoundedBar(
                left = left,
                baselineY = baselineY,
                width = barWidth,
                height = barHeight,
                color = barColor.copy(alpha = 0.7f)
            )

            // Format and draw X-axis label
            val displayLabel = when (tabItem) {
                TabItem.Monthly -> {
                    val day = label.split(" ")[1].toInt()
                    if (day % 2 == 0) label.split(" ")[1] else ""
                }
                else -> label
            }

            val labelSize = textMeasurer.measure(displayLabel)
            val labelWidth = labelSize.size.width
            val labelX = left + (barWidth - labelWidth) / 2

            drawText(
                textMeasurer = textMeasurer,
                text = displayLabel,
                topLeft = Offset(labelX, baselineY + 8.dp.toPx()),
                style = TextStyle(
                    color = textColor,
                    fontSize = 10.sp
                )
            )
        }
    }
}

// Updated helper function to draw rounded bar with correct positioning
private fun DrawScope.drawRoundedBar(
    left: Float,
    baselineY: Float,
    width: Float,
    height: Float,
    color: Color
) {
    val cornerRadius = 12f
    val path = Path().apply {
        // Start from the baseline
        moveTo(left, baselineY)

        // Draw left side up to the rounded corner
        lineTo(left, baselineY - height + cornerRadius)

        // Draw top-left rounded corner
        quadraticTo(
            left,
            baselineY - height,
            left + cornerRadius,
            baselineY - height
        )

        // Draw top line
        lineTo(left + width - cornerRadius, baselineY - height)

        // Draw top-right rounded corner
        quadraticTo(
            left + width,
            baselineY - height,
            left + width,
            baselineY - height + cornerRadius
        )

        // Draw right side down to baseline
        lineTo(left + width, baselineY)

        // Close the path
        close()
    }

    drawPath(path, color, style = Fill)
}

// Helper function to group expenses by day
private fun groupExpensesByDay(
    expenses: List<Expense>,
    start: LocalDate,
    end: LocalDate
): Map<String, Double> {
    val dayFormatter = DateTimeFormatter.ofPattern("MMM d")

    // Generate all dates in the range
    val allDates = generateSequence(start) { it.plusDays(1) }
        .takeWhile { !it.isAfter(end) }
        .map { it.format(dayFormatter) }
        .toList()

    // Group expenses
    val expenseMap = expenses
        .filter {
            val expenseDate = LocalDate.parse(it.data.date)
            expenseDate in start..end
        }
        .groupBy {
            LocalDate.parse(it.data.date).format(dayFormatter)
        }
        .mapValues { (_, groupedExpenses) ->
            groupedExpenses.sumOf { it.data.amount }
        }

    // Ensure all dates are represented
    return allDates.associateWith {
        expenseMap.getOrDefault(it, 0.0)
    }
}

// Helper function to group expenses by month
private fun groupExpensesByMonth(
    expenses: List<Expense>,
    start: LocalDate,
    end: LocalDate
): Map<String, Double> {
    val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    // Generate all months in the range
    val allMonths = generateSequence(start.withDayOfMonth(1)) { it.plusMonths(1) }
        .takeWhile { !it.isAfter(end.withDayOfMonth(1)) }
        .map { it.format(monthFormatter) }
        .toList()

    // Group expenses
    val expenseMap = expenses
        .filter {
            val expenseDate = LocalDate.parse(it.data.date)
            expenseDate in start..end
        }
        .groupBy {
            LocalDate.parse(it.data.date).format(monthFormatter)
        }
        .mapValues { (_, groupedExpenses) ->
            groupedExpenses.sumOf { it.data.amount }
        }

    // Ensure all months are represented
    return allMonths.associateWith {
        expenseMap.getOrDefault(it, 0.0)
    }
}