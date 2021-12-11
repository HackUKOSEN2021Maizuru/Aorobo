package com.example.aorobo.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentHomeBinding;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.StudyTimeDataBaseSingleton;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.db.time.TimeDBDao;
import com.example.aorobo.ui.time.TimeFragment;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private StudyTimeDataBase db;
    private TextView timeText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

         */
        return root;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = StudyTimeDataBaseSingleton.getInstance(null);
        System.out.println("fasdfafa");
        Bundle args = getArguments();

        timeText=getActivity().findViewById(R.id.study_time);
        new DataStoreAsyncTask(db, getActivity(), timeText,0).execute();



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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        Date date =new Date();
        System.out.println(date);
        date = new Date(date.getYear(),date.getMonth(),date.getDay());
        System.out.println(date);

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
                System.out.println("compair");
                System.out.println(at.getdate());
                System.out.println(date);
                if(at.getdate().getYear()==date.getYear()&&at.getdate().getDay()==date.getDay()&&at.getdate().getMonth()==date.getMonth()){
                    times+=at.gettime();
                }
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