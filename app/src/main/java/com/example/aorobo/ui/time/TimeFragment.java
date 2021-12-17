package com.example.aorobo.ui.time;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentScheduleBinding;
import com.example.aorobo.databinding.FragmentTimeBinding;
import com.example.aorobo.db.AppDatabase;
import com.example.aorobo.db.AppDatabaseSingleton;
import com.example.aorobo.db.FavorabilityDataBase;
import com.example.aorobo.db.FavorabilityDataBaseSingleton;
import com.example.aorobo.db.LogDao;
import com.example.aorobo.db.Log;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.StudyTimeDataBaseSingleton;
import com.example.aorobo.db.TimeDatabase;
import com.example.aorobo.db.TimeDatabaseSingleton;
import com.example.aorobo.db.favorability.Favorability;
import com.example.aorobo.db.favorability.FavorabilityDao;
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
    private TextView startText;
    private TextView log;
    private long count, delay, period;
    private String zero;
    private WeakReference<Activity> weakActivity;
    private StudyTimeDataBase db;
    private RequestManager glide;
    FragmentTimeBinding binding;

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
        binding = FragmentTimeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;

    }
    @Override
    public void onResume() {
        super.onResume();

        db = StudyTimeDataBaseSingleton.getInstance(null);
        Bundle args = getArguments();

        delay = 0;
        period = 100;
        // "00:00.0"
        zero = getString(R.string.zero);
        count=0;
        timerText = getActivity().findViewById(R.id.TimeText);
        timerText.setText(zero);
        startText=getActivity().findViewById(R.id.StartButton);
        //logText = getActivity().findViewById(R.id.LogText);
        new DataStoreAsyncTask(db, getActivity(),0).execute();
        glide=Glide.with(this);

        Button startButton = getActivity().findViewById(R.id.StartButton);
        ImageView studyimage=getActivity().findViewById(R.id.imageView4);
        studyimage.setImageResource(R.raw.study_end);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // タイマーが走っている最中にボタンをタップされたケース
                if(null != timer){
                    timer.cancel();
                    timer = null;
                    startButton.setText("再開");

                }
                else if(count!=0){
                    timer = new Timer();

                    // TimerTask インスタンスを生成
                    timerTask = new TimeFragment.CountUpTimerTask();

                    timer.schedule(timerTask, delay, period);
                    startButton.setText("一時停止");
                }
                else{
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

                    startButton.setText("一時停止");
                }



            }
        });

        // タイマー終了
        Button stopButton = getActivity().findViewById(R.id.StopButton);
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // timer がnullでない、起動しているときのみcancleする
                if(count>0){

                    DataStoreAsyncTask ds =new DataStoreAsyncTask(db, getActivity(),count);
                    ds.execute();
                    System.out.println("gettimes:");
                    //long l=
                    //logText.setText(getTimeString(ds.getTimes()));
                    // Cancel
                    if(timer!=null){
                        timer.cancel();
                        timer = null;
                    }

                    timerText.setText(zero);
                    count = 0;
                    startButton.setText("計測開始");

                    glide.load(R.raw.study_end)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))

                            .into(new DrawableImageViewTarget(getActivity().findViewById(R.id.imageView4)) {
                                @Override
                                public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    if (resource instanceof GifDrawable) {
                                        ((GifDrawable) resource).setLoopCount(1);
                                    }
                                    super.onResourceReady(resource, transition);
                                }
                            });





                }
            }
        });
    }


    public String getTimeString(long time){
        /*
        long mm = time*100 / 1000 / 60;
        long ss = time*100 / 1000 % 60;
        long ms = (time*100 - ss * 1000 - mm * 1000 * 60)/100;

         */

        long mm = time*100 / 1000 / 60/60;
        long ss = (time*100 / 1000 / 60)%60;
        long ms = (time*100 / 1000) % 60;

        return String.format(Locale.US, "%1$01d:%2$02d:%3$02d", mm, ss, ms);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        db = StudyTimeDataBaseSingleton.getInstance(null);
        Bundle args = getArguments();

        delay = 0;
        period = 100;
        // "00:00.0"
        zero = getString(R.string.zero);
        count=0;
        timerText = getActivity().findViewById(R.id.TimeText);
        timerText.setText(zero);
        startText=getActivity().findViewById(R.id.StartButton);
        //logText = getActivity().findViewById(R.id.LogText);
        new DataStoreAsyncTask(db, getActivity(),0).execute();
        glide=Glide.with(this);

        Button startButton = getActivity().findViewById(R.id.StartButton);
        ImageView studyimage=getActivity().findViewById(R.id.imageView4);
        studyimage.setImageResource(R.raw.study_end);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // タイマーが走っている最中にボタンをタップされたケース
                if(null != timer){
                    timer.cancel();
                    timer = null;
                    startButton.setText("再開");

                }
                else if(count!=0){
                    timer = new Timer();

                    // TimerTask インスタンスを生成
                    timerTask = new TimeFragment.CountUpTimerTask();

                    timer.schedule(timerTask, delay, period);
                    startButton.setText("一時停止");
                }
                else{
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

                    startButton.setText("一時停止");
                }



            }
        });

        // タイマー終了
        Button stopButton = getActivity().findViewById(R.id.StopButton);
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // timer がnullでない、起動しているときのみcancleする
                if(count>0){

                    DataStoreAsyncTask ds =new DataStoreAsyncTask(db, getActivity(),count);
                    ds.execute();
                    System.out.println("gettimes:");
                    //long l=
                    //logText.setText(getTimeString(ds.getTimes()));
                    // Cancel
                    if(timer!=null){
                        timer.cancel();
                        timer = null;
                    }

                    timerText.setText(zero);
                    count = 0;
                    startButton.setText("計測開始");

                    glide.load(R.raw.study_end)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))

                            .into(new DrawableImageViewTarget(getActivity().findViewById(R.id.imageView4)) {
                                @Override
                                public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    if (resource instanceof GifDrawable) {
                                        ((GifDrawable) resource).setLoopCount(1);
                                    }
                                    super.onResourceReady(resource, transition);
                                }
                            });





                }
            }
        });*/



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
        //private TextView textView;
        private StringBuilder sb;
        //String s;
        long s;
        long times;
        long i;
        private FavorabilityDataBase fdb;

        public DataStoreAsyncTask(StudyTimeDataBase db, Activity activity,long s) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            //this.textView = textView;
            this.s=s;
            fdb = FavorabilityDataBaseSingleton.getInstance(null);


        }

        @Override
        protected Integer doInBackground(Void... params) {
            TimeDBDao timeDBDao = db.TimeDBDao();
            FavorabilityDao favorabilityDao=fdb.FavorabilityDao();
            //timeDBDao.nukeTable();
            Date date =new Date();
            System.out.println("date");
            System.out.println(date);
            //date = new Date(date.getYear(),date.getMonth(),date.getDay());
            System.out.println(date);
            if(s>0){

                timeDBDao.insert(new TimeDB(s,date));
                long min=s/10000/60;
                Random rand = new Random();
                long fav=0;
                if(min>=20){
                    fav=min/6+ rand.nextInt(5)-2;
                }else{
                    int f=rand.nextInt(5);
                    if(f==0){
                        fav=rand.nextInt(6)-10;
                    }else{
                        fav=min/6+ rand.nextInt(5)-2;
                    }
                }
                favorabilityDao.insert(new Favorability(fav,date));

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


            //textView.setText(String.format(Locale.US, "%1$02d:%2$02d.%3$01d", mm, ss, ms));

        }
        public long getTimes(){
            System.out.println("ans:");
            System.out.println(times);
            return times;
        }
    }



}