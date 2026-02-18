package com.mohammadalmomani.modevlib.dateTimePicker
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentActivity
import androidx.core.util.Pair
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

/**
 * DatePicker is a custom date selection dialog using MaterialDatePicker:
 * - Supports single or range date selection
 * - Custom locale
 * - Custom date format
 * - Minimum and maximum date offsets
 *
 * ## Features:
 * - Uses MaterialDatePicker under the hood
 * - Allows dynamic control of single/range picker
 * - Supports listener callbacks for positive, cancel, and dismiss actions
 *
 * ## How to Use:
 *
 * ### Single Date Picker:
 * ```kotlin
 * DatePicker.builder(this)
 *     .singlePicker()            // Single date picker
 *     .setLocale(Locale("ar"))       // Arabic locale
 *     .setDateFormat("dd/MM/yyyy")   // Custom date format
 *     .minDate(-2)                   // Min 2 days before today
 *     .setDatePickerInterface {
 *         onPositive = { date ->
 *             Log.d("TAG", "Selected date: $date")
 *         }
 *         onCancel = {
 *             // Handle cancel
 *         }
 *         onDismiss = {
 *             // Handle dismiss
 *         }
 *     }
 *     .build(supportFragmentManager, "SINGLE_PICKER")
 * ```
 *
 * ### Range Date Picker:
 * ```kotlin
 * DatePicker.builder(this)
 *     .rangePicker()             // Range picker
 *     .setLocale(Locale("ar"))
 *     .setDateFormat("dd/MM/yyyy")
 *     .setDateRangePickerInterface {
 *         onPositive = { start, end ->
 *             Log.d("TAG", "Selected range: $start -> $end")
 *         }
 *         onCancel = {
 *             // Handle cancel
 *         }
 *         onDismiss = {
 *             // Handle dismiss
 *         }
 *     }
 *     .build(supportFragmentManager, "RANGE_PICKER")
 * ```
 *
 * ### Notes:
 * - `minDate` and `maxDate` are relative to today in days.
 * - If `minDate` or `maxDate` is 0, the default is today / today+30 days.
 * - Uses milliseconds internally to configure MaterialDatePicker constraints.
 */

class DatePicker private constructor(
    private val activity: FragmentActivity
) {

    companion object {
        fun builder(activity: FragmentActivity): DatePicker {
            return DatePicker(activity)
        }
    }

    private var locale: Locale = Locale.ENGLISH
    private var dateFormat: String = "dd-MM-yyyy"
    private var isRange = false
    private var constraintsBuilder: CalendarConstraints.Builder? = null

    private var singleInterface: SingleDateInterface? = null
    private var rangeInterface: RangeDateInterface? = null

    // --------------------------------------------------
    // Picker Type
    // --------------------------------------------------
    fun singlePicker(): DatePicker {
        isRange = false
        return this
    }

    fun rangePicker(): DatePicker {
        isRange = true
        return this
    }

    // --------------------------------------------------
    // Config
    // --------------------------------------------------
    fun setLocale(locale: Locale): DatePicker {
        this.locale = locale
        return this
    }

    fun setDateFormat(format: String): DatePicker {
        this.dateFormat = format
        return this
    }

    // --------------------------------------------------
    // Interfaces
    // --------------------------------------------------
    fun setDatePickerInterface(block: SingleDateInterface.() -> Unit): DatePicker {
        singleInterface = SingleDateInterface().apply(block)
        return this
    }

    fun setDateRangePickerInterface(block: RangeDateInterface.() -> Unit): DatePicker {
        rangeInterface = RangeDateInterface().apply(block)
        return this
    }

    // --------------------------------------------------
    // Single picker only
    // --------------------------------------------------
    fun minDate(day: Int): DatePicker {
        if (isRange) {
            // Ignore minDate for range picker
            return this
        }

        val minDay = System.currentTimeMillis() + (day - 1) * 24 * 60 * 60 * 1000
        constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.from(minDay))
        return this
    }

    // --------------------------------------------------
    // Build
    // --------------------------------------------------
    fun build(fragmentManager: FragmentManager, tag: String) {

        if (!isRange) {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder?.build())
                .build()

            picker.addOnPositiveButtonClickListener { millis ->
                val formatted = format(millis)
                singleInterface?.onPositive?.invoke(formatted)
            }

            picker.addOnCancelListener { singleInterface?.onCancel?.invoke() }
            picker.addOnDismissListener { singleInterface?.onDismiss?.invoke() }

            picker.show(fragmentManager, tag)

        } else {
            val picker = MaterialDatePicker.Builder.dateRangePicker().build()

            picker.addOnPositiveButtonClickListener { selection: Pair<Long, Long>? ->
                selection?.let {
                    val start = format(it.first)
                    val end = format(it.second)
                    rangeInterface?.onPositive?.invoke(start, end)
                }
            }

            picker.addOnCancelListener { rangeInterface?.onCancel?.invoke() }
            picker.addOnDismissListener { rangeInterface?.onDismiss?.invoke() }

            picker.show(fragmentManager, tag)
        }
    }

    private fun format(millis: Long): String {
        val sdf = SimpleDateFormat(dateFormat, locale)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(millis))
    }

    // --------------------------------------------------
    // Interface Classes
    // --------------------------------------------------
    class SingleDateInterface {
        var onPositive: ((String) -> Unit)? = null
        var onCancel: (() -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
    }

    class RangeDateInterface {
        var onPositive: ((String, String) -> Unit)? = null
        var onCancel: (() -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
    }
}
