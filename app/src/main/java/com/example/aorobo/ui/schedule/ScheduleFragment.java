package com.example.aorobo.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.aorobo.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog.OnDateSetListener;

public class ScheduleFragment extends Fragment implements OnDateSetListener {
    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule,
                container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = getActivity().findViewById(R.id.daytext);
        Button startButton = getActivity().findViewById(R.id.datebutton);
        Calendar c=Calendar.getInstance();
        c.set(2021,01,20);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment  datePicker = new DatePick(ScheduleFragment.this);
                System.out.println("click");
                datePicker.show(getChildFragmentManager(), "datePicker");

            }

        });

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        System.out.println("ondataset");
        String str = String.format(Locale.US, "%d/%d/%d",year, monthOfYear+1, dayOfMonth);
        textView.setText( str );

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }






}
