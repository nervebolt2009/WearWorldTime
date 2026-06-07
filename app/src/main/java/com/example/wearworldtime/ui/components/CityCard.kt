package com.example.wearworldtime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.IconButton
import androidx.wear.compose.material.Text
import com.example.wearworldtime.data.City
import com.example.wearworldtime.ui.theme.*
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Composable
fun CityCard(
    city: City,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Keep timezone calculations inside remember blocks keyed on current system time to avoid redundant objects
    val zoneId = remember(city.timeZoneId) { ZoneId.of(city.timeZoneId) }
    val now = ZonedDateTime.now(zoneId)
    val hour = now.hour

    // Determine rich gradient and matching icon for local timezone status
    val (backgroundBrush, timeIcon, iconColor) = remember(hour) {
        when (hour) {
            in 7..16 -> Triple(
                Brush.radialGradient(listOf(DayGradStart, DayGradEnd)),
                Icons.Default.WbSunny,
                AccentGold
            )
            in 5..6, in 17..19 -> Triple(
                Brush.radialGradient(listOf(DuskGradStart, DuskGradEnd)),
                Icons.Default.WbTwilight,
                SoftPink
            )
            else -> Triple(
                Brush.radialGradient(listOf(NightGradStart, NightGradEnd)),
                Icons.Default.NightsStay,
                PrimaryTeal
            )
        }
    }

    // Format local time of city
    val timeString = remember(now) {
        now.format(DateTimeFormatter.ofPattern("hh:mm"))
    }
    val amPmString = remember(now) {
        now.format(DateTimeFormatter.ofPattern("a"))
    }

    // Compute relative offset hours to current system default
    val localZone = remember { ZoneId.systemDefault() }
    val localTime = ZonedDateTime.now(localZone)
    val offsetDiffSeconds = remember(now, localTime) {
        Duration.between(localTime.toInstant(), now.toInstant()).seconds
    }
    val offsetString = remember(offsetDiffSeconds) {
        val diffHours = offsetDiffSeconds / 3600.0
        if (abs(diffHours) < 0.1) {
            "Home Time"
        } else {
            val prefix = if (diffHours > 0) "+" else "-"
            val absDiff = abs(diffHours)
            val hours = absDiff.toInt()
            val mins = ((absDiff - hours) * 60).toInt()
            if (mins == 0) {
                "$prefix${hours}h"
            } else {
                "$prefix${hours}h ${mins}m"
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(backgroundBrush)
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Left block: Name & offset details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = timeIcon,
                        contentDescription = "Day/Night Indicator",
                        tint = iconColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = city.abbreviation,
                        color = TextPrimary,
                        style = Typography.title3
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = city.name,
                    color = TextPrimary,
                    style = Typography.body1,
                    maxLines = 1
                )
                Text(
                    text = offsetString,
                    color = TextSecondary,
                    style = Typography.caption2
                )
            }

            // Right block: Local Time & Quick Delete
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(
                        text = timeString,
                        color = TextPrimary,
                        style = Typography.title2
                    )
                    Text(
                        text = amPmString,
                        color = iconColor,
                        style = Typography.caption2
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove City",
                        tint = TextSecondary.copy(alpha = 0.6f),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
