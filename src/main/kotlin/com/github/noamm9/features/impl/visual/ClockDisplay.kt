package com.github.noamm9.features.impl.visual

import com.github.noamm9.features.Feature
import com.github.noamm9.ui.clickgui.components.getValue
import com.github.noamm9.ui.clickgui.components.impl.ColorSetting
import com.github.noamm9.ui.clickgui.components.impl.ToggleSetting
import com.github.noamm9.ui.clickgui.components.provideDelegate
import com.github.noamm9.utils.render.Render2D
import com.github.noamm9.utils.render.Render2D.height
import com.github.noamm9.utils.render.Render2D.width
import java.awt.Color
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object ClockDisplay: Feature("Displays the system time on screen.") {
    private val minutes by ToggleSetting("Show Minutes", true)
    private val seconds by ToggleSetting("Show Seconds", true)
    private val twelveHour by ToggleSetting("12-Hour Format", false)
    private val color by ColorSetting("Color", Color(255, 134, 0), false)

    private val clockDisplayHud = hudElement("ClockDisplay") { ctx, _ ->
        val hourFormat = if (twelveHour.value) "h" else "HH"
        val pattern = buildString {
            append(hourFormat)
            if (minutes.value) append(":mm")
            if (minutes.value && seconds.value) append(":ss")
            if (twelveHour.value) append(" a")
        }
        val text = LocalTime.now()
            .format(DateTimeFormatter.ofPattern(pattern, Locale.US))
            .lowercase()
        Render2D.drawString(ctx, text, 0, 0, color.value)
        return@hudElement text.width().toFloat() to text.height().toFloat()
    }
}