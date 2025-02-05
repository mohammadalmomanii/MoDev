package com.mohammadalmomani.modevlib.support;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.core.os.LocaleListCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.topSheetDialog.TopSheetBehavior;
import com.mohammadalmomani.modevlib.topSheetDialog.TopSheetDialog;
import com.mohammadalmomani.modevlib.topSheetDialog.TopSheetDialogFragment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppHelper {

    /**
     * use the new Function getCurrentDate.
     *
     * @deprecated See {@link AppHelper#getCurrentDate(String)}
     * <p>
     * you can add your format to get your specific date.
     */
    @Deprecated
    static public String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }

    /**
     * Gets the current date in the specified format.
     *
     * @param format The format in which the date should be returned.
     * @return A string representing the current date in the specified format.
     */
    static public String getCurrentDate(String format) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }

    /**
     * Sets the default time zone for the application.
     *
     * @param GMT The time zone to set, e.g., "GMT+03:00".
     */
    static public void setTimeZone(String GMT) {
        TimeZone.setDefault(TimeZone.getTimeZone(GMT.replaceAll("[^GMT+:0123456789]", "")));
    }

    /**
     * Determines the shift based on the current time.
     *
     * @return A string representing the shift ("A", "B", or "C").
     */
    static public String getShift() {
        int time = Integer.parseInt(new SimpleDateFormat("HH", Locale.ENGLISH).format(new Date()));
        if (time >= 8 && time < 16)
            return "A";
        else if (time >= 16 && time < 24)
            return "B";
        else return "C";
    }

    /**
     * Determines the shift based on the provided date and time.
     *
     * @param date The date and time string to evaluate.
     * @return A string representing the shift ("A", "B", or "C").
     */
    static public String getShift(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        int hour = dateTime.getHour();

        if (hour >= 8 && hour < 16)
            return "A";
        else if (hour >= 16 && hour < 24)
            return "B";
        else
            return "C";
    }

    /**
     * Calculates the number of days between two dates.
     *
     * @param date1 The first date in "yyyy-MM-dd" format.
     * @param date2 The second date in "yyyy-MM-dd" format.
     * @return The number of days between the two dates.
     */
    static public long getDaysBetweenDates(String date1, String date2) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = null;   // initialize start date
        Date endDate = null;   // initialize start date
        try {
            startDate = myFormat.parse(date1);
            endDate = myFormat.parse(date2); // initialize  end date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long duration = endDate.getTime() - startDate.getTime();

        return Math.abs(TimeUnit.MILLISECONDS.toDays(duration));
    }

    /**
     * Opens a file from the Documents directory using an appropriate application.
     *
     * @param context  The context from which the file is being opened.
     * @param fileName The name of the file to open.
     */
    static public void openFile(Context context, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(uri, "application/pdf");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(i);
    }

    /**
     * Hides the soft keyboard if it is currently displayed.
     *
     * @param activity The activity from which the keyboard should be hidden.
     */
    static public void hideKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Displays a Snackbar with the specified message.
     *
     * @param view    The view to anchor the Snackbar to.
     * @param message The message to display in the Snackbar.
     * @return The view on which the Snackbar was displayed.
     */
    static public View showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAnchorView(view).show();
        return view;
    }

    /**
     * Sets the application's night mode.
     *
     * @param value If true, enables night mode; otherwise, disables it.
     */
    static public void setAppModeNight(boolean value) {
        AppCompatDelegate.setDefaultNightMode(value ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * Checks if the application is currently in night mode.
     *
     * @param context The context to check the mode for.
     * @return True if the application is in night mode, false otherwise.
     */
    static public boolean getAppMode(Context context) {
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO)
            return true;
        else
            return false;
    }

    /**
     * Sets the visibility of a view with a transition animation.
     *
     * @param view       The view to animate.
     * @param visibility The visibility to set (e.g., View.VISIBLE, View.GONE).
     * @param transition The transition to apply.
     * @param duration   The duration of the transition in milliseconds.
     * @return The view that was animated.
     */
    static public View setAnimateVisibility(View view, int visibility, Transition transition, long duration) {
        Transition transition1 = transition;
        transition.setDuration(duration);
        transition.addTarget(view);
        TransitionManager.beginDelayedTransition((ViewGroup) view.getParent(), transition);
        view.setVisibility(visibility);
        return view;
    }

    /**
     * Sets the margins of a view.
     *
     * @param view   The view to set margins for.
     * @param left   The left margin in pixels.
     * @param top    The top margin in pixels.
     * @param right  The right margin in pixels.
     * @param bottom The bottom margin in pixels.
     * @return The view with updated margins.
     */
    static public View setMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        p.setMargins(left, top, right, bottom);
        view.requestLayout();
        return view;
    }

    /**
     * Expands a BottomSheetDialogFragment to its full height.
     *
     * @param fragment The BottomSheetDialogFragment to expand.
     */
    static public void expandedBottomSheetDialog(BottomSheetDialogFragment fragment) {
        BottomSheetDialog dialog = (BottomSheetDialog) fragment.getDialog();
        dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /**
     * Expands a TopSheetDialogFragment to its full height.
     *
     * @param fragment The TopSheetDialogFragment to expand.
     */
    static public void expandedTopSheetDialog(TopSheetDialogFragment fragment) {
        TopSheetDialog dialog = (TopSheetDialog) fragment.getDialog();
        dialog.setState(TopSheetBehavior.STATE_EXPANDED);
    }

    /**
     * use the new Function getCurrentDate.
     *
     * @deprecated See {@link AppHelper#setAnimation(View, int, long)}
     * <p>
     * you can add your format to get your specific date.
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    static public View setAnimation(Context context, View view, int res, long duration) {
        Animation animation = AnimationUtils.loadAnimation(context, res);
        animation.setDuration(duration);
        view.startAnimation(animation);
        return view;
    }

    /**
     * Applies an animation to a view.
     *
     * @param view     The view to animate.
     * @param res      The resource ID of the animation to apply.
     * @param duration The duration of the animation in milliseconds.
     * @return The view that was animated.
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    static public View setAnimation(View view, int res, long duration) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), res);
        animation.setDuration(duration);
        view.startAnimation(animation);
        return view;
    }

    /**
     * Applies a custom animation to a view, allowing for horizontal and vertical movement.
     *
     * @param view     The view to animate.
     * @param vertical The vertical movement value.
     * @param horizontal The horizontal movement value.
     * @param duration The duration of the animation in milliseconds.
     * @return The view that was animated.
     */
    static public View setAnimation2(View view, Float vertical, Float horizontal, long duration) {
        ViewGroup.MarginLayoutParams vlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.animate().x(vertical == null ? vlp.rightMargin | vlp.leftMargin : vertical).
                y(horizontal == null ? vlp.topMargin : horizontal).setDuration(duration).start();
        return view;
    }

    /**
     * Removes focus from the currently focused view in the activity.
     *
     * @param context The activity from which to remove focus.
     */
    static public void removeFocus(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Sets custom activity transition animations.
     *
     * @param activity The activity to apply the animations to.
     * @param newAnim  The resource ID of the enter animation.
     * @param oldAnim  The resource ID of the exit animation.
     */
    static public void activityMoveAnimation(Activity activity, int newAnim, int oldAnim) {
        activity.overridePendingTransition(newAnim, oldAnim);
    }

    /**
     * use the new Function showDateAndTimePickersDialog.
     *
     * @deprecated See {@link AppHelper#showDateAndTimePickersDialog(Context, String, String, MainInterface.DialogPicker)}
     * <p>
     * this function provide more flexibility and easy to control with picker dialog.
     */
    @Deprecated
    static public void dateTimePicker(Context context, TextView view, String format) {
        datePicker(context, view, format).setOnDismissListener(dialog -> {
            if (format.equals("yyyy-MM-dd")) timePicker(context, view, false);
        });
    }

    /**
     * use the new Function showDateAndTimePickersDialog.
     *
     * @deprecated See {@link AppHelper#showDateAndTimePickersDialog(Context, String, String, MainInterface.DialogPicker)}
     * <p>
     * this function provide more flexibility and easy to control with picker dialog.
     */
    @Deprecated
    static public DatePickerDialog datePicker(Context context, TextView view2, String format) {
        view2.setText("");
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year1, month1, dayOfMonth) -> {
                    int newMonth = month1 + 1;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String dateInString = dayOfMonth + "-" + newMonth + "-" + year1;
                        Date date1 = formatter.parse(dateInString);
                        formatter = new SimpleDateFormat(format, Locale.ENGLISH);
                        view2.setText(formatter.format(date1));
                    } catch (Exception e) {
                    }
                }, year, month, day
        );
        datePickerDialog.show();
        return datePickerDialog;
    }

    /**
     * use the new Function showDateAndTimePickersDialog.
     *
     * @deprecated See {@link AppHelper#showDateAndTimePickersDialog(Context, String, String, MainInterface.DialogPicker)}
     * <p>
     * this function provide more flexibility and easy to control with picker dialog.
     */
    @Deprecated
    static public TimePickerDialog timePicker(Context context, TextView view2, boolean showDate) {
        TimePickerDialog timePicker = new TimePickerDialog(context,
                (timePicker1, selectedHour, selectedMinute) -> {
                    Calendar time = Calendar.getInstance();
                    time.set(Calendar.HOUR_OF_DAY, selectedHour);
                    time.set(Calendar.MINUTE, selectedMinute);
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                    view2.setText(view2.getText() + "   " + (showDate ? getCurrentDate("yyyy-MM-dd") + "\t" : "") + format.format(time.getTime()));
                }, 1, 1, false);// Yes 24 hour time
        timePicker.setTitle(context.getString(R.string.time));
        timePicker.show();
        return timePicker;
    }

    /**
     * Displays a DatePickerDialog with the specified locale and format.
     *
     * @param context     The context to display the dialog in.
     * @param locale      The locale to use for the dialog.
     * @param format      The format to use for the date.
     * @param dialogPicker The callback to handle the dialog dismissal.
     * @return The DatePickerDialog that was shown.
     */
    private static DatePickerDialog showDatePickerDialog(Context context, Locale locale, String format, MainInterface.DialogPicker dialogPicker) {
        final Calendar calendar = Calendar.getInstance();
        final String[] formattedDate = new String[1];

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                R.style.Modev_DialogPicker,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
                    formattedDate[0] = dateFormat.format(calendar.getTime());
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
        datePickerDialog.setOnDismissListener(dialog -> dialogPicker.onDialogDismiss(formattedDate[0]));

        return datePickerDialog;
    }

    /**
     * Displays a TimePickerDialog with the specified locale and format.
     *
     * @param context     The context to display the dialog in.
     * @param locale      The locale to use for the dialog.
     * @param format      The format to use for the time.
     * @param dialogPicker The callback to handle the dialog dismissal.
     * @return The TimePickerDialog that was shown.
     */
    private static TimePickerDialog showTimePickerDialog(Context context, Locale locale, String format, MainInterface.DialogPicker dialogPicker) {
        final Calendar calendar = Calendar.getInstance();
        final String[] formattedTime = new String[1];

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                R.style.Modev_DialogPicker,
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    SimpleDateFormat timeFormat = new SimpleDateFormat(format, locale);
                    formattedTime[0] = timeFormat.format(calendar.getTime());
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        );

        timePickerDialog.show();
        timePickerDialog.setOnDismissListener(dialog -> dialogPicker.onDialogDismiss(formattedTime[0]));

        return timePickerDialog;
    }

    /**
     * Displays a DatePickerDialog and/or a TimePickerDialog based on the provided formats.
     *
     * @param context      The context to display the dialogs in.
     * @param dateFormat   The format to use for the date (nullable).
     * @param timeFormat   The format to use for the time (nullable).
     * @param dialogPicker The callback to handle the dialog dismissals.
     */
    public static void showDateAndTimePickersDialog
    (Context context, @Nullable String dateFormat, @Nullable String timeFormat
            , MainInterface.DialogPicker dialogPicker) {
        if (dateFormat != null && timeFormat != null) {
            showDatePickerDialog(context, Locale.getDefault(), dateFormat.replaceAll("[^-:GyYuMLwWDdFEaHhKkmsSzZX /|\\\\]", ""), new MainInterface.DialogPicker() {
                @Override
                public void onDialogDismiss(Object object) {
                    final String[] date = {""};
                    if (object != null)
                        date[0] += object;
                    showTimePickerDialog(context, Locale.getDefault(), timeFormat, new MainInterface.DialogPicker() {
                        @Override
                        public void onDialogDismiss(Object object) {
                            if (object != null && !date[0].isEmpty())
                                date[0] += " | " + object;
                            else if (object != null)
                                date[0] += object;
                            dialogPicker.onDialogDismiss(date[0]);
                        }
                    });
                }
            });
        } else if (dateFormat != null)
            showDatePickerDialog(context, Locale.getDefault(), dateFormat.replaceAll("[^-:GyYuMLwWDdFEaHhKkmsSzZX /|\\\\]", ""), dialogPicker);
        else if (timeFormat != null)
            showTimePickerDialog(context, Locale.getDefault(), timeFormat.replaceAll("[^-:GyYuMLwWDdFEaHhKkmsSzZX /|\\\\]", ""), dialogPicker);
    }

    /**
     * Displays a DatePickerDialog and/or a TimePickerDialog with a specified locale.
     *
     * @param context      The context to display the dialogs in.
     * @param locale       The locale to use for the dialogs.
     * @param dateFormat   The format to use for the date (nullable).
     * @param timeFormat   The format to use for the time (nullable).
     * @param dialogPicker The callback to handle the dialog dismissals.
     */
    public static void showLocaleDateAndTimePickersDialog
    (Context context, Locale locale, @Nullable String dateFormat, @Nullable String timeFormat
            , MainInterface.DialogPicker dialogPicker) {

        if (dateFormat != null && timeFormat != null) {
            showDatePickerDialog(context, locale, dateFormat.replaceAll("[^-:GyYuMLwWDdFEaHhKkmsSzZX /|\\\\]", ""), new MainInterface.DialogPicker() {
                @Override
                public void onDialogDismiss(Object object) {
                    final String[] date = {""};
                    if (object != null)
                        date[0] += object;
                    showTimePickerDialog(context, locale, timeFormat.replaceAll("[^-:GyYuMLwWDdFEaHhKkmsSzZX /|\\\\]", ""), new MainInterface.DialogPicker() {
                        @Override
                        public void onDialogDismiss(Object object) {
                            if (object != null && !date[0].isEmpty())
                                date[0] += " | " + object;
                            else if (object != null)
                                date[0] += object;
                            dialogPicker.onDialogDismiss(date[0]);
                        }
                    });
                }
            });
        } else if (dateFormat != null)
            showDatePickerDialog(context, locale, dateFormat, dialogPicker);
        else if (timeFormat != null)
            showTimePickerDialog(context, locale, timeFormat, dialogPicker);
    }

    /**
     * use the new Function setLanguage_NEW.
     *
     * @deprecated See {@link AppHelper#setLanguage_NEW(String)}
     * <p>
     * in new function you don't need to restart activity.
     */
    @Deprecated
    static public void setLanguage(Context context, String language) {
        Configuration configuration = context.getResources().getConfiguration();
        Locale newLocale = new Locale(language);
        configuration.setLocale(newLocale);
        context.createConfigurationContext(configuration);
    }

    /**
     * Sets the application's language without requiring an activity restart.
     *
     * @param language The language code to set (e.g., "en", "ar").
     */
    static public void setLanguage_NEW(String language) {
        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.create(Locale.forLanguageTag(language))
        );
    }

    /**
     * Sets the visibility of a view to VISIBLE.
     *
     * @param view The view to modify.
     * @return The view with updated visibility.
     */
    static public View setVisible(View view) {
        view.setVisibility(View.VISIBLE);
        return view;
    }

    /**
     * Sets the visibility of a view to GONE.
     *
     * @param view The view to modify.
     * @return The view with updated visibility.
     */
    static public View setGone(View view) {
        view.setVisibility(View.GONE);
        return view;
    }

    /**
     * Sets the visibility of a view to INVISIBLE.
     *
     * @param view The view to modify.
     * @return The view with updated visibility.
     */
    static public View setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
        return view;
    }

    /**
     * Gets the current position of the first completely visible item in a RecyclerView.
     *
     * @param recyclerView The RecyclerView to check.
     * @return The position of the first completely visible item.
     */
    static public int recycleViewGetCurrentPosition(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        return (linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);
    }

    /**
     * Prevents a RecyclerView from scrolling or responding to touch events.
     *
     * @param recyclerView The RecyclerView to freeze.
     */
    static public void freezeRecyclerView(RecyclerView recyclerView) {
        recyclerView.setOnTouchListener((v, event) -> true);
    }

    /**
     * Executes a Runnable on a background thread after a specified delay.
     *
     * @param runnable    The Runnable to execute.
     * @param delayMillis The delay in milliseconds before execution.
     */
    static public void delayOnBackground(Runnable runnable, long delayMillis) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(runnable, delayMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Executes a Runnable on the main thread after a specified delay.
     *
     * @param runnable    The Runnable to execute.
     * @param delayMillis The delay in milliseconds before execution.
     */
    static public void delay(Runnable runnable, long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(runnable, delayMillis);
    }

    /**
     * Logs and returns the Android device's brand.
     *
     * @return The device's brand in lowercase.
     */
    static public String getAndroidVersion() {
        Log.d("APPPP_RELESE", Build.VERSION.RELEASE + "");
        Log.d("APPPP_SDK_INT", Build.VERSION.SDK_INT + "");
        Log.d("APPPP_BRAND", Build.BRAND.toLowerCase(Locale.ENGLISH));

        return Build.BRAND.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Displays a Toast message in case of failure.
     *
     * @param context The context to display the Toast in.
     * @param s       The message to display.
     * @param b       If true, appends a connection timeout message.
     */
    static public void onFailure(Context context, String s, boolean b) {
        if (!b) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getString(R.string.connection_timeout) + " : " + s, Toast.LENGTH_LONG).show();
        }
    }
}
