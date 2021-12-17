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
import com.example.aorobo.db.FavorabilityDataBase;
import com.example.aorobo.db.FavorabilityDataBaseSingleton;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.favorability.Favorability;
import com.example.aorobo.db.favorability.FavorabilityDao;
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
import java.util.Random;

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
    }
}
