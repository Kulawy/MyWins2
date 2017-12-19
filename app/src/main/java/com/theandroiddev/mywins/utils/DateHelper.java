package com.theandroiddev.mywins.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.theandroiddev.mywins.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.theandroiddev.mywins.utils.Constants.DATE;
import static com.theandroiddev.mywins.utils.Constants.DATE_FORMAT;


/**
 * Created by jakub on 03.09.17.
 */

public class DateHelper {

    private Calendar myCalendar;
    private Context context;

    public DateHelper(Context context) {
        this.context = context;
    }

    public void setDate(final String d, final TextView dateStarted, final TextView dateEnded) {

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(d, dateStarted, dateEnded);
            }

        };

        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void updateLabel(String d, TextView dateStarted, TextView dateEnded) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        if (d.equals(context.getString(R.string.date_started_empty))) {
            dateStarted.setText(sdf.format(myCalendar.getTime()));
            dateStarted.setError(null);
        }
        if (d.equals(context.getString(R.string.date_ended_empty))) {
            dateEnded.setText(sdf.format(myCalendar.getTime()));
            dateEnded.setError(null);

        }

    }

    public String checkBlankDate(String s) {
        if (s.contains(DATE)) {
            return "";
        } else return s;
    }

    public boolean validateData(EditText titleEt, TextView dateStartedTv, TextView dateEndedTv) {

        int cnt = 0;

        if (TextUtils.isEmpty(titleEt.getText().toString())) {
            titleEt.setError(context.getString(R.string.error_title));
            cnt++;

        }

        if (!dateStartedTv.getText().toString().contains("Date")) {
            if (!TextUtils.isEmpty(dateStartedTv.getText().toString()) && !TextUtils.isEmpty(dateEndedTv.getText().toString())) {
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                try {
                    Date date1 = sdf.parse(dateStartedTv.getText().toString());
                    Date date2 = sdf.parse(dateEndedTv.getText().toString());
                    if (date1.after(date2)) {
                        dateEndedTv.setError(context.getString(R.string.error_date_ended));
                        cnt++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }

        return cnt <= 0;

    }

}
