package com.mohammadalmomani.modevlib.dateTimePicker

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*



/**
 * TimePicker is a custom dialog for selecting a time using MaterialTimePicker with:
 * - Optional 24-hour or 12-hour format
 * - Optional locale for formatting
 * - Optional clock-only or keyboard input mode
 * - Fluent API for easy configuration
 *
 * ## Features:
 * - Uses MaterialTimePicker for consistent Material 3 style
 * - Supports locale-specific formatting
 * - Supports chaining of configuration functions like setLocale(), setTimeFormat(), setIs24Hour(), and setInputMode()
 * - Provides callbacks for positive, cancel, and dismiss actions
 *
 * ## How to Use:
 *
 * ### 1. Show TimePicker using the Builder:
 * ```kotlin
 * TimePicker.builder(this)
 *     .setIs24Hour(true)                        // 24-hour format enabled
 *     .setLocale(Locale("ar"))                  // Use Arabic locale
 *     .setTimeFormat("HH:mm")                   // Custom time format
 *     .setInputMode(TimePicker.InputMode.CLOCK) // Force clock input only, no keyboard
 *     .setTimePickerInterface {
 *         onTimeSelected = { time ->
 *             Log.d("TimePicker", "Selected time: $time")
 *         }
 *         onCancel = {
 *             Log.d("TimePicker", "Canceled")
 *         }
 *         onDismiss = {
 *             Log.d("TimePicker", "Dismissed")
 *         }
 *     }
 *     .build(supportFragmentManager, "TIME_PICKER")
 * ```
 *
 * ### 2. Notes:
 * - `setInputMode(TimePicker.InputMode.CLOCK)` forces the picker to use clock input only.
 * - `setTimeFormat()` controls how the selected time is returned as a string.
 * - Locale affects formatting of time if using AM/PM or other locale-specific formats.
 * - Callbacks are optional; you can provide only the ones you need.
 */

class TimePicker private constructor(
    private val activity: FragmentActivity
) {

    companion object {
        fun builder(activity: FragmentActivity): TimePicker {
            return TimePicker(activity)
        }
    }

    enum class InputMode {
        CLOCK, // Only clock selection
        KEYBOARD // Allows keyboard input (default)
    }

    private var locale: Locale = Locale.ENGLISH
    private var timeFormat: String = "HH:mm"
    private var is24Hour: Boolean = true
    private var inputMode: InputMode = InputMode.CLOCK

    private var timeInterface: TimeInterface? = null

    // --------------------------------------------------
    // Config
    // --------------------------------------------------
    fun setLocale(locale: Locale): TimePicker {
        this.locale = locale
        return this
    }

    fun setTimeFormat(format: String): TimePicker {
        this.timeFormat = format
        return this
    }

    fun setIs24Hour(is24Hour: Boolean): TimePicker {
        this.is24Hour = is24Hour
        return this
    }

    fun setInputMode(mode: InputMode): TimePicker {
        this.inputMode = mode
        return this
    }

    // --------------------------------------------------
    // Interface
    // --------------------------------------------------
    fun setTimePickerInterface(block: TimeInterface.() -> Unit): TimePicker {
        timeInterface = TimeInterface().apply(block)
        return this
    }

    // --------------------------------------------------
    // Build
    // --------------------------------------------------
    fun build(fragmentManager: FragmentManager, tag: String) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(if (is24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
            .setMinute(Calendar.getInstance().get(Calendar.MINUTE))
            .setTitleText("Select Time")
            .setInputMode(
                when (inputMode) {
                    InputMode.CLOCK -> MaterialTimePicker.INPUT_MODE_CLOCK
                    InputMode.KEYBOARD -> MaterialTimePicker.INPUT_MODE_KEYBOARD
                }
            )
            .build()



        picker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, picker.hour)
            calendar.set(Calendar.MINUTE, picker.minute)
            val formatted = format(calendar.time)
            timeInterface?.onTimeSelected?.invoke(formatted)
        }

        picker.addOnCancelListener { timeInterface?.onCancel?.invoke() }
        picker.addOnDismissListener { timeInterface?.onDismiss?.invoke() }

        picker.show(fragmentManager, tag)
    }

    private fun format(date: Date): String {
        val sdf = SimpleDateFormat(timeFormat, locale)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    // --------------------------------------------------
    // Interface
    // --------------------------------------------------
    class TimeInterface {
        var onTimeSelected: ((String) -> Unit)? = null
        var onCancel: (() -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
    }
}
