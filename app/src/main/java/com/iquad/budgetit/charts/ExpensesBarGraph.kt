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

        val padding = 40.dp.toPx() // Increased padding for Y-axis labels
        val bottomPadding = 32.dp.toPx()
        val graphWidth = canvasWidth - 2 * padding
        val graphHeight = canvasHeight - bottomPadding

        val maxAmount = groupedExpenses.values.maxOrNull() ?: 0.0

        val barWidth = graphWidth / groupedExpenses.size * 0.8f
        val scaleFactor = (graphHeight - 20.dp.toPx()) / maxAmount

        // Draw Y-axis labels
        val yAxisLabelCount = 5
        for (i in 0 until yAxisLabelCount) {
            val yValue = (maxAmount * i / (yAxisLabelCount - 1)).roundToInt()
            val yPos = graphHeight - (yValue * scaleFactor) + bottomPadding

            drawText(
                textMeasurer = textMeasurer,
                text = yValue.toString(),
                topLeft = Offset(10.dp.toPx(), yPos.toFloat()),
                style = TextStyle(
                    color = textColor,
                    fontSize = 10.sp
                )
            )

            // Optional: Draw light grid lines
            drawLine(
                color = Color.LightGray.copy(alpha = 0.3f),
                start = Offset(padding, yPos.toFloat()),
                end = Offset(canvasWidth - padding, yPos.toFloat())
            )
        }

        groupedExpenses.entries.forEachIndexed { index, (label, amount) ->
            val barHeight = amount * scaleFactor
            val left = padding + index * (graphWidth / groupedExpenses.size)

            // Draw rounded bar
            drawRoundedBar(
                left = left,
                bottom = (canvasHeight - barHeight - bottomPadding).toFloat(),
                width = barWidth,
                height = barHeight.toFloat(),
                color = barColor.copy(alpha = 0.7f)
            )

            // Modify label based on tab item
            val displayLabel = when (tabItem) {
                TabItem.Monthly -> {
                    val day = label.split(" ")[1].toInt()
                    if (day % 2 == 0) label.split(" ")[1] else ""
                }
                else -> label
            }

            // Measure label width for centering
            val labelSize = textMeasurer.measure(displayLabel)
            val labelWidth = labelSize.size.width
            val labelX = left + (barWidth - labelWidth) / 2

            // Draw label
            drawText(
                textMeasurer = textMeasurer,
                text = displayLabel,
                topLeft = Offset(labelX, canvasHeight - 24.dp.toPx()),
                style = TextStyle(
                    color = textColor,
                    fontSize = 10.sp
                )
            )
        }
    }
}

// Helper function to draw rounded bar
private fun DrawScope.drawRoundedBar(
    left: Float,
    bottom: Float,
    width: Float,
    height: Float,
    color: Color
) {
    val cornerRadius = 12f
    val path = Path().apply {
        moveTo(left, bottom + cornerRadius)

        // Top left rounded corner
        quadraticTo(
            left,
            bottom,
            left + cornerRadius,
            bottom
        )

        // Top line
        lineTo(left + width - cornerRadius, bottom)

        // Top right rounded corner
        quadraticTo(
            left + width,
            bottom,
            left + width,
            bottom + cornerRadius
        )

        // Right line
        lineTo(left + width, bottom + height)

        // Bottom line
        lineTo(left, bottom + height)

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