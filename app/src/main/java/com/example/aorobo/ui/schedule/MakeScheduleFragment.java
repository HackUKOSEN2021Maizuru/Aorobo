package com.example.aorobo.ui.schedule;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.aorobo.R;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.TimeDatabase;
import com.example.aorobo.db.TimeDatabaseSingleton;
import com.example.aorobo.db.schedule.ScheduleDB;
import com.example.aorobo.db.schedule.ScheduleDBDao;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.db.time.TimeDBDao;
import com.example.aorobo.ui.time.TimeFragment;

import java.lang.ref.WeakReference;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MakeScheduleFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    TextView startText,endText;
    boolean isDateSetEnd;
    Date startDay=null,endDay=null;
    String scheduleName=null;
    private ScheduleDataBase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("createview");
        isDateSetEnd=false;


        return inflater.inflate(R.layout.fragment_make_schedule,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("viewcreated");
        Bundle args = getArguments();
        Button startbutton=getActivity().findViewById(R.id.button_schedule_start);
        Button endbutton=getActivity().findViewById(R.id.button_schedule_end);
        startText=getActivity().findViewById(R.id.button_schedule_start);
        endText=getActivity().findViewById(R.id.button_schedule_end);
        db = ScheduleDataBaseSingleton.getInstance(null);


        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDateSetEnd=false;
                DialogFragment  datePicker = new DatePick(MakeScheduleFragment.this);
                System.out.println("click");
                datePicker.show(getChildFragmentManager(), "datePicker");

            }

        });
        endbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDateSetEnd=true;

                DialogFragment datePicker = new DatePick(MakeScheduleFragment.this);
                System.out.println("click");
                datePicker.show(getChildFragmentManager(), "datePicker");

            }

        });


        Button saveButton = view.findViewById(R.id.savebutton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText scheduleNameText=getActivity().findViewById(R.id.input_schedule_title);
                SpannableStringBuilder sb = (SpannableStringBuilder)scheduleNameText.getText();
                scheduleName= sb.toString();
                if(startDay!=null&&endDay!=null&&scheduleName!=null){
                    System.out.println(startDay);
                    System.out.println(endDay);
                    System.out.println(scheduleName);
                    new DataStoreAsyncTask(db, getActivity(),startDay,endDay,scheduleName).execute();
                    Navigation.findNavController(v).navigate(R.id.action_make_to_schedule);
                }else{
                    System.out.println("NULL!!!");
                }

            }

        });

        /*
        button02.setOnClickListener( v -> {
            FragmentManager fragmentManager = getFragmentManager();
            if(fragmentManager != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // BackStackを設定
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.replace(R.id.container, ScheduleFragment.newInstance());
                fragmentTransaction.commit();
            }
        });

         */
        /*
        // BackStackで１つ戻す
        Button pop02 = view.findViewById(R.id.pop_02);
        pop02.setOnClickListener( v -> {
            FragmentManager fragmentManager = getFragmentManager();
            if(fragmentManager != null) {
                fragmentManager.popBackStack();
            }
        });

         */
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        System.out.println("ondataset");
        String str = String.format(Locale.US, "%d/%d/%d",year, monthOfYear+1, dayOfMonth);
        if(isDateSetEnd==false){
            startText.setText( str );
            startDay=new Date(year-1900,monthOfYear,dayOfMonth);
        }else{
            endText.setText( str );
            endDay=new Date(year-1900,monthOfYear,dayOfMonth);
        }


    }

    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private ScheduleDataBase db;
        Date startDay,endDay;
        String scheduleName;

        public DataStoreAsyncTask(ScheduleDataBase db, Activity activity, Date startDay,Date endDay,String scheduleName) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.startDay=startDay;
            this.endDay=endDay;
            this.scheduleName=scheduleName;


        }

        @Override
        protected Integer doInBackground(Void... params) {
            ScheduleDBDao scheduleDBDao = db.ScheduleDBDao();
            //timeDBDao.nukeTable();

            scheduleDBDao.insert(new ScheduleDB(scheduleName,startDay,endDay));





            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }


        }

    }


}
