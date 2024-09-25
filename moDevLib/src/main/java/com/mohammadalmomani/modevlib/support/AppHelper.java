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

    static public String getCurrentDate(String format) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }

    /**
     * setTimeZone.
     *
     * @param GTM the time value to be formatted into a date-time string.
     *            Like "GTM+03:00", "GTM-04:00", "GTM+02:00" ets.
     * @return the formatted date-time string.
     */
    static public void setTimeZone(String GTM) {
        TimeZone.setDefault(TimeZone.getTimeZone(GTM));
    }

    static public String getShift() {
        int time = Integer.parseInt(new SimpleDateFormat("HH", Locale.ENGLISH).format(new Date()));
        if (time >= 8 && time < 16)
            return "A";
        else if (time >= 16 && time < 24)
            return "B";
        else return "C";
    }

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
    static public void openFile(Context context, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(uri, "application/pdf");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(i);


    }
    static public void hideKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    static public void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAnchorView(view).show();
        }

    static public void setAppModeNight(boolean value) {
        AppCompatDelegate.setDefaultNightMode(value ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    static public boolean getAppMode(Context context) {
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO)
            return true;
        else
            return false;
    }

    static public void setAnimateVisibility(View view, int visibility, Transition transition, long duration) {
//                                       (binding.toolbarCard, View.GONE, new Slide(Gravity.TOP), 500);

//        Transition transition1 = new Fade();
        Transition transition1 = transition;
        transition.setDuration(duration);
        transition.addTarget(view);
        TransitionManager.beginDelayedTransition((ViewGroup) view.getParent(), transition);
        view.setVisibility(visibility);
    }

    static public void setMargin(View view, int left, int top, int right, int bottom) {


        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        p.setMargins(left, top, right, bottom);
        view.requestLayout();

    }

    static public void expandedBottomSheetDialog(BottomSheetDialogFragment fragment) {
        BottomSheetDialog dialog = (BottomSheetDialog) fragment.getDialog();
        dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    static public void expandedTopSheetDialog(TopSheetDialogFragment fragment) {
        TopSheetDialog dialog = (TopSheetDialog) fragment.getDialog();
        dialog.setState(TopSheetBehavior.STATE_EXPANDED);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    static public void setAnimation(Context context, View view, int res, long duration) {
        Animation animation = AnimationUtils.loadAnimation(context, res);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    static public void setAnimation2(View view, Float vertical, Float horizontal, long duration) {
        ViewGroup.MarginLayoutParams vlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.animate().x(vertical == null ? vlp.rightMargin | vlp.leftMargin : vertical).
                y(horizontal == null ? vlp.topMargin : horizontal).setDuration(duration).start();

        // CAN USE ANIMATION IN THIS WAY TOO
//        view.animate().x(vertical==null?vlp.rightMargin|vlp.leftMargin:vertical).
//                y(horizontal==null?vlp.topMargin:horizontal).setDuration(duration).setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        //type Code Here
//                    }
//                });


    }

    static public void removeFocus(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    static public void activityMoveAnimation(Activity activity, int newAnim, int oldAnim) {
        activity.overridePendingTransition(newAnim, oldAnim);
    }

    /**
     * use the new Function showDateAndTimePickersDialog.
     *
     * @deprecated See {@link AppHelper#showDateAndTimePickersDialog(Context, String, String, MainInterface)}
     *
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
     * @deprecated See {@link AppHelper#showDateAndTimePickersDialog(Context, String, String, MainInterface)}
     *
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
//        make date start from today
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
        return datePickerDialog;
    }

    /**
     * use the new Function showDateAndTimePickersDialog.
     *
     * @deprecated See {@link AppHelper#showDateAndTimePickersDialog(Context, String, String, MainInterface)}
     *
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

    private static DatePickerDialog showDatePickerDialog(Context context, String format, MainInterface mainInterface) {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        final String[] formattedDate = new String[1];

        // Create DatePickerDialog

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                R.style.Modev_DialogPickerTheme,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Update the calendar with the selected date
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Format the selected date in any format (e.g., dd-MM-yyyy)
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
                    formattedDate[0] = dateFormat.format(calendar.getTime());

                    // Set the formatted date to the TextView

                },
                // Set initial date for the DatePickerDialog
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
        datePickerDialog.setOnDismissListener(dialog -> mainInterface.onDialogDismiss(formattedDate[0]));

        return datePickerDialog;
    }

    private static TimePickerDialog showTimePickerDialog(Context context, String format, MainInterface mainInterface) {
        // Get current time
        final Calendar calendar = Calendar.getInstance();
        final String[] formattedTime = new String[1];
        // Create TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                R.style.Modev_DialogPickerTheme, // Apply the custom dialog theme here
                (view, hourOfDay, minute) -> {
                    // Update the calendar with the selected time
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    // Format the selected time in any format (e.g., HH:mm or hh:mm a)
                    SimpleDateFormat timeFormat = new SimpleDateFormat(format, Locale.getDefault());
                    formattedTime[0] = timeFormat.format(calendar.getTime());

                    // Set the formatted time to the TextView
                },
                // Set initial time for the TimePickerDialog
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false  // is24HourView: false for 12-hour format, true for 24-hour format
        );

        // Show the TimePickerDialog
        timePickerDialog.show();
        timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                mainInterface.onDialogDismiss(formattedTime[0]);
            }
        });

        return timePickerDialog;
    }
    public static void showDateAndTimePickersDialog
            (Context context, @Nullable String dateFormat, @Nullable String timeFormat
                    , MainInterface mainInterface) {

        if (dateFormat != null && timeFormat != null) {
            showDatePickerDialog(context, dateFormat, new MainInterface() {
                @Override
                public void onDialogDismiss(Object object) {
                    final String[] date = {""};
                    if (object != null)
                        date[0] += object;
                    showTimePickerDialog(context, timeFormat, new MainInterface() {
                        @Override
                        public void onDialogDismiss(Object object) {
                            if (object != null && !date[0].isEmpty())
                                date[0] += " | " + object;
                            else if (object != null)
                                date[0] += object;
                            mainInterface.onDialogDismiss(date[0]);
                        }
                    });
                }
            });
        } else if (dateFormat != null)
            showDatePickerDialog(context, dateFormat, mainInterface);
        else if (timeFormat != null)
            showTimePickerDialog(context, timeFormat, mainInterface);

    }
    /**
     * use the new Function setLanguage_NEW.
     *
     * @deprecated See {@link AppHelper#setLanguage_NEW(String)}
     *
     * in new function you don't need to restart activity.
     */
    @Deprecated
    static public void setLanguage(Context context, String language) {
        Configuration configuration = context.getResources().getConfiguration();
        Locale newLocale = new Locale(language);
        configuration.setLocale(newLocale);
        context.createConfigurationContext(configuration);
    }

    static public void setLanguage_NEW(String language) {
        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.create(Locale.forLanguageTag(language))
        );
    }

    static public View setVisible(View view) {
        view.setVisibility(View.VISIBLE);
        return view;
    }

    static public View setGone(View view) {
        view.setVisibility(View.GONE);
    return view;}

    static public void setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }


    static public int recycleViewGetCurrentPosition(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        return (linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);
    }

    static public void freezeRecyclerView(RecyclerView recyclerView) {
        recyclerView.setOnTouchListener((v, event) -> true);
    }



    static public void delayOnBackground(Runnable runnable, long delayMillis) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(runnable, delayMillis, TimeUnit.MILLISECONDS);
    }

    static public void delay(Runnable runnable, long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(runnable, delayMillis);

    }

    static public String getAndroidVersion() {
        Log.d("APPPP_RELESE", Build.VERSION.RELEASE + "");
        Log.d("APPPP_SDK_INT", Build.VERSION.SDK_INT + "");
        Log.d("APPPP_BRAND", Build.BRAND.toLowerCase(Locale.ENGLISH));

        return Build.BRAND.toLowerCase(Locale.ENGLISH);
    }

    static public void onFailure(Context context, String s, boolean b) {
        if (!b) {
            Toast.makeText(context,  s, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getString(R.string.connection_timeout) + " : " + s, Toast.LENGTH_LONG).show();
        }
    }


}
