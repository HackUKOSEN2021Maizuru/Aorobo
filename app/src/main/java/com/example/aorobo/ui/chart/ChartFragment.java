package com.example.aorobo.ui.chart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentBluetoothBinding;
import com.example.aorobo.databinding.FragmentChartBinding;
import com.example.aorobo.databinding.FragmentGalleryBinding;
import com.example.aorobo.databinding.FragmentSlideshowBinding;
import com.example.aorobo.db.FavorabilityDataBase;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.StudyTimeDataBaseSingleton;
import com.example.aorobo.db.schedule.ScheduleDB;
import com.example.aorobo.db.schedule.ScheduleDBDao;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.db.time.TimeDBDao;
import com.example.aorobo.ui.gallery.GalleryViewModel;
import com.example.aorobo.ui.slideshow.SlideshowViewModel;
import com.example.aorobo.ui.today.TodayAdapter;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import android.content.Intent;
import android.content.Context;

public class ChartFragment extends Fragment {

    private FragmentChartBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View v=getActivity().findViewById(R.id.chartdata1);
        System.out.println("get:"+v.getY());
        System.out.println("pivot:"+v.getPivotY());
        System.out.println("scale:"+v.getScaleY());
        v.setScaleY(2);
        View v2=getActivity().findViewById(R.id.chartdata2);
        v2.setScaleY(1/2);

    }
    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private StudyTimeDataBase db;
        //private TextView textView;
        private StringBuilder sb;
        //String s;
        long s;
        long favorability;
        long i;
        private FavorabilityDataBase fdb;
        Activity activity;
        RecyclerView recyclerView;
        static List<String> list= new ArrayList<String>();

        public DataStoreAsyncTask(Activity activity,long s,RecyclerView recyclerView) {
            // this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.activity=activity;
            this.recyclerView =recyclerView;
            //this.textView = textView;
            this.s=s;
            //fdb = FavorabilityDataBaseSingleton.getInstance(null);
            db= StudyTimeDataBaseSingleton.getInstance(null);

        }

        @Override
        protected Integer doInBackground(Void... params) {


            Date date =new Date();
            System.out.println("date");
            System.out.println(date);
            //date = new Date(date.getYear(),date.getMonth(),date.getDay());
            System.out.println(date);

            TimeDBDao timeDBDao = db.TimeDBDao();


            i=0;
            System.out.println("get");
            List<TimeDB> atList = timeDBDao.getAll();
            System.out.println("got");
            int data[]=new int[7];
            Arrays.fill(data,0);
            for (TimeDB at: atList) {

            }





            return 0;
        }
        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }
            System.out.println("a");


            System.out.println("b");
            //TodayAdapter todayAdapter = new TodayAdapter(list);
            //scheduleAdapter.setHasStableIds(true);


            //recyclerView.setAdapter(todayAdapter);
            //listsSwipe();
            /*
            adapter = new SimpleAdapter(
                    context,
                    items,
                    R.layout.row,
                    new String [] {"name", "time"},
                    new int[] {R.id.schedule_name, R.id.schedule_limit}
            );

            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
            System.out.println("c");

             */



        }
    }

}
