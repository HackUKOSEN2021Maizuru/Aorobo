package com.example.aorobo.ui.schedule;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.aorobo.MainActivity;

import java.util.Calendar;

public class DatePick extends DialogFragment implements
        DatePickerDialog.OnDateSetListener{

    DatePickerDialog.OnDateSetListener listener;

    public DatePick(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),
                listener,  year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year,
                          int monthOfYear, int dayOfMonth) {
    }



}