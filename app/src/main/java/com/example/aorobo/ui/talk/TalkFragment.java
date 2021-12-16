package com.example.aorobo.ui.talk;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentTalkBinding;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.schedule.ScheduleDB;
import com.example.aorobo.db.schedule.ScheduleDBDao;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.db.time.TimeDBDao;
import com.example.aorobo.ui.home.HomeAdapter;
import com.example.aorobo.ui.schedule.ScheduleFragment;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TalkFragment extends Fragment{

    private FragmentTalkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTalkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
    static void loadImage(RequestManager glide, int url, ImageView view) {
        glide.load(url).into(view);
    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //listView=getActivity().findViewById(R.id.shedule_list);

        ImageView imageView  = getActivity().findViewById(R.id.gifView);

        //ImageView matchImage = getActivity().findViewById(R.id.image_view);
        loadImage(Glide.with(this), R.raw.colob_rolling2, getActivity().findViewById(R.id.gifView));
        //Glide.with(this.getContext()).load(R.raw.colob_roll).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }

        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private StudyTimeDataBase db;
        private ScheduleDataBase sdb;
        private TextView textView;
        private StringBuilder sb;
        //String s;
        long s;
        long times;
        long i;
        RecyclerView recyclerView;
        RecyclerView recyclerViewIcon;

        ListView listView;
        Context context;
        static Activity activity;

        static List<String> iName = new ArrayList<String>();
        static List<String> iDate = new ArrayList<String>();
        static List<String> icnt = new ArrayList<String>();
        static SimpleAdapter adapter;


        public DataStoreAsyncTask(StudyTimeDataBase db, Activity activity, TextView textView,long s,ScheduleDataBase sdb,RecyclerView recyclerView,Context context) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.textView = textView;
            this.s=s;
            this.sdb=sdb;
            this.recyclerView=recyclerView;
            this.context=context;
            this.activity=activity;


        }

        @Override
        protected Integer doInBackground(Void... params) {
            TimeDBDao timeDBDao = db.TimeDBDao();
            ScheduleDBDao scheduleDBDao = sdb.ScheduleDBDao();
            //timeDBDao.nukeTable();
            Date date =new Date();
            System.out.println("date");
            System.out.println(date);
            //date = new Date(date.getYear(),date.getMonth(),date.getDay());
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
            List<ScheduleDB> sList = scheduleDBDao.getAll();
            System.out.println("got");
            iName.clear();
            iDate.clear();
            icnt.clear();
            date=new Date();
            System.out.println("fafdsaffsfsdf");
            icnt.add("");
            icnt.add("");

            for (ScheduleDB at: sList) {
                //Map<String,String> data = new HashMap();
                //data.put("name",at.getName());
                long t=(at.getEnd().getTime()-date.getTime())/1000/60/60/24;
                iName.add(at.getName());
                iDate.add(String.format(Locale.US, "残り%1$02d日", t));
                //data.put("time",String.format(Locale.US, "残り%1$02d日", t));

                System.out.println(at.getName());
                System.out.println(at.getStart());
                System.out.println(at.getEnd());
                System.out.println(date);
                System.out.println((at.getEnd().getTime()-date.getTime())/1000/60/60/24);


            }





            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }
            long mm = times*100 / 1000 / 60/60;
            long ss = (times*100 / 1000 / 60)%60;
            long ms = (times*100 / 1000) % 60;

            textView.setText(String.format(Locale.US, "%1$02d:%2$02d", mm, ss));


            System.out.println("c");

        }
        public long getTimes(){
            System.out.println("ans:");
            System.out.println(times);
            return times;
        }
    }
}
