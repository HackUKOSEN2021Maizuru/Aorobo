package com.example.aorobo.ui.time;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.Looper;
import com.example.aorobo.R;
import com.example.aorobo.db.AppDatabase;
import com.example.aorobo.db.AppDatabaseSingleton;
import com.example.aorobo.db.LogDao;
import com.example.aorobo.db.Log;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.StudyTimeDataBaseSingleton;
import com.example.aorobo.db.TimeDatabase;
import com.example.aorobo.db.TimeDatabaseSingleton;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.db.time.TimeDBDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.security.Timestamp;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;
//import com.example.koroboandroidapp.ui.dashboard.DashboardViewModel;
import android.widget.ImageButton;


public class TimeFragment extends Fragment {
    private int cnt = 0;
    private Timer timer;
    private CountUpTimerTask timerTask;
    // 'Handler()' is deprecated as of API 30: Android 11.0 (R)
    private final Handler handler = new Handler(Looper.getMainLooper());

    private TextView timerText;
    private TextView logText;
    private TextView log;
    private long count, delay, period;
    private String zero;
    private WeakReference<Activity> weakActivity;
    private StudyTimeDataBase db;

    static TimeFragment newInstance(int count){
        // Fragemnt01 インスタンス生成
        TimeFragment fragment01 = new TimeFragment();

        // Bundle にパラメータを設定
        Bundle args = new Bundle();
        args.putInt("Counter", count);
        fragment01.setArguments(args);

        return fragment01;
    }

    // FragmentのViewを生成して返す
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time,
                container, false);
    }


    public String getTimeString(long time){
        long mm = time*100 / 1000 / 60;
        long ss = time*100 / 1000 % 60;
        long ms = (time*100 - ss * 1000 - mm * 1000 * 60)/100;

        return String.format(Locale.US, "%1$02d:%2$02d.%3$01d", mm, ss, ms);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = StudyTimeDataBaseSingleton.getInstance(null);
        Bundle args = getArguments();

        delay = 0;
        period = 100;
        // "00:00.0"
        zero = getString(R.string.zero);
        timerText = getActivity().findViewById(R.id.TimeText);
        timerText.setText(zero);
        logText = getActivity().findViewById(R.id.LogText);
        new DataStoreAsyncTask(db, getActivity(), logText,0).execute();




        ImageButton startButton = getActivity().findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // タイマーが走っている最中にボタンをタップされたケース
                if(null != timer){
                    timer.cancel();
                    timer = null;
                }

                // Timer インスタンスを生成
                timer = new Timer();

                // TimerTask インスタンスを生成
                timerTask = new TimeFragment.CountUpTimerTask();

                // スケジュールを設定 100msec
                // public void schedule (TimerTask task, long delay, long period)
                timer.schedule(timerTask, delay, period);

                // カウンター
                count = 0;
                timerText.setText(zero);

            }
        });

        // タイマー終了
        ImageButton stopButton = getActivity().findViewById(R.id.StopButton);
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // timer がnullでない、起動しているときのみcancleする
                if(null != timer){

                    DataStoreAsyncTask ds =new DataStoreAsyncTask(db, getActivity(), logText,count);
                    ds.execute();
                    System.out.println("gettimes:");
                    //long l=
                    //logText.setText(getTimeString(ds.getTimes()));
                    // Cancel
                    timer.cancel();
                    timer = null;
                    timerText.setText(zero);
                    count = 0;


                }
            }
        });



        // BackStackで１つ戻す
        /*
        Button pop01 = view.findViewById(R.id.pop_01);
        pop01.setOnClickListener( v -> {
            FragmentManager fragmentManager = getFragmentManager();
            if(fragmentManager != null) {
                fragmentManager.popBackStack();
            }
        });
        */
    }




    class CountUpTimerTask extends TimerTask {
        @Override
        public void run() {
            // handlerを使って処理をキューイングする
            handler.post(new Runnable() {
                public void run() {
                    count++;
                    // 桁数を合わせるために02d(2桁)を設定
                    timerText.setText(getTimeString(count));


                }
            });
        }
    }
    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private StudyTimeDataBase db;
        private TextView textView;
        private StringBuilder sb;
        //String s;
        long s;
        long times;
        long i;

        public DataStoreAsyncTask(StudyTimeDataBase db, Activity activity, TextView textView,long s) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.textView = textView;
            this.s=s;


        }

        @Override
        protected Integer doInBackground(Void... params) {
            TimeDBDao timeDBDao = db.TimeDBDao();
            //timeDBDao.nukeTable();
            Date date =new Date();
            System.out.println("date");
            System.out.println(date);
            date = new Date(date.getYear(),date.getMonth(),date.getDay());
            System.out.println(date);
            if(s>0){

                timeDBDao.insert(new TimeDB(s,date));
            }
            System.out.println("今回の勉強時間");
            System.out.println(s);
            sb = new StringBuilder();


                times=0;
                i=0;
            System.out.println("get");
            List<TimeDB> atList = timeDBDao.getAll();
            System.out.println("got");
                for (TimeDB at: atList) {
                    System.out.println(i);

                    times+=at.gettime();
                    System.out.println(at.getId());
                    System.out.println(at.gettime());


                    i++;
                }
                System.out.println(i);




            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }
            long mm = times*100 / 1000 / 60;
            long ss = times*100 / 1000 % 60;
            long ms = (times*100 - ss * 1000 - mm * 1000 * 60)/100;

            ;


            textView.setText(String.format(Locale.US, "%1$02d:%2$02d.%3$01d", mm, ss, ms));

        }
        public long getTimes(){
            System.out.println("ans:");
            System.out.println(times);
            return times;
        }
    }



}