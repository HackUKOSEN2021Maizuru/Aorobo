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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
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

        new DataStoreAsyncTask(getActivity()).execute();
        /*
        View v=getActivity().findViewById(R.id.chartdata1);
        System.out.println("get:"+v.getY());
        System.out.println("pivot:"+v.getPivotY());
        System.out.println("scale:"+v.getScaleY());
        v.setScaleY(2);
        View v2=getActivity().findViewById(R.id.chartdata2);
        v2.setScaleY(1/2);
        */

    }
    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private StudyTimeDataBase db;
        //private TextView textView;
        private StringBuilder sb;
        //String s;
        long s;
        long favorability;
        private FavorabilityDataBase fdb;
        Activity activity;
        RecyclerView recyclerView;
        static List<String> list= new ArrayList<String>();

        public DataStoreAsyncTask(Activity activity) {
            // this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.activity=activity;
            //this.textView = textView;
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


            System.out.println("get");
            List<TimeDB> atList = timeDBDao.getAll();
            System.out.println("got");
            long data[]=new long[7];
            Arrays.fill(data,0);
            for (TimeDB at: atList) {
                long diff=date.getTime()-at.date.getTime();
                if(diff/1000/60/60/24<7){

                    data[(int)diff/1000/60/60/24]+=at.time;
                }
            }

            List<Integer> chartlist=new ArrayList<Integer>();
            chartlist.add(R.id.chartdata1);
            chartlist.add(R.id.chartdata2);
            chartlist.add(R.id.chartdata3);
            chartlist.add(R.id.chartdata4);
            chartlist.add(R.id.chartdata5);
            chartlist.add(R.id.chartdata6);
            chartlist.add(R.id.chartdata7);
            //data[3]=10*60*230;

            List<Integer> textlist=new ArrayList<Integer>();
            textlist.add(R.id.scale1_h);
            textlist.add(R.id.scale2_h);
            textlist.add(R.id.scale3_h);
            textlist.add(R.id.scale4_h);
            textlist.add(R.id.scale5_h);
            textlist.add(R.id.scale6_h);
            textlist.add(R.id.scale7_h);
            List<Integer> vtextlist=new ArrayList<Integer>();
            vtextlist.add(R.id.scale1_v);
            vtextlist.add(R.id.scale2_v);
            vtextlist.add(R.id.scale3_v);
            vtextlist.add(R.id.scale4_v);
            vtextlist.add(R.id.scale5_v);

            for(int i=0;i<4;i++){
                TextView tv=activity.findViewById(vtextlist.get(i));
                tv.setText(String.format(Locale.US, "%1$02d", 60*(i+1)));

            }


            for(int i=0;i<7;i++){
                View v=activity.findViewById(chartlist.get(i));
                System.out.println("datai:"+data[6-i]);
                v.setScaleY(data[6-i]/10/60*2);
                Date d2=new Date();
                d2.setTime(date.getTime()-1000*60*60*24*(6-i));
                TextView tv=activity.findViewById(textlist.get(i));
                tv.setText(new SimpleDateFormat("EEE").format(d2));

            }
            System.out.println("DATE:"+ new SimpleDateFormat("MM.dd").format(date));





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
