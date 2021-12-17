package com.example.aorobo.ui.today;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentTodayBinding;
import com.example.aorobo.db.FavorabilityDataBase;
import com.example.aorobo.db.FavorabilityDataBaseSingleton;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.StudyTimeDataBaseSingleton;
import com.example.aorobo.db.favorability.Favorability;
import com.example.aorobo.db.favorability.FavorabilityDao;
import com.example.aorobo.db.schedule.ScheduleDB;
import com.example.aorobo.db.schedule.ScheduleDBDao;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.db.time.TimeDBDao;
import com.example.aorobo.ui.gallery.GalleryViewModel;
import com.example.aorobo.ui.schedule.ScheduleAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodayFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentTodayBinding binding;
    private Handler mHandler = new Handler();
    /** テキストオブジェクト */
    private Runnable updateText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentTodayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        loadImage(Glide.with(this), R.raw.colob_roll, getActivity().findViewById(R.id.today_gif));
    }
    static void loadImage(RequestManager glide, int url, ImageView view) {
        glide.load(url).into(view);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        /*
        ImageButton imageButton = view.findViewById(R.id.app_start_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_title_to_home);
            }
        });
        */

        RecyclerView recyclerView=getActivity().findViewById(R.id.today_schedule_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(rLayoutManager);


        new DataStoreAsyncTask(getActivity(),0,recyclerView).execute();
        Button button =getActivity().findViewById(R.id.to_home_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_today_to_home);
            }
        });
        /*
        updateText = new Runnable() {
            public void run() {
                Navigation.findNavController(view).navigate(R.id.action_today_to_home);
            }
        };
        mHandler.postDelayed(updateText, 3000);

         */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //((AppCompatActivity)getActivity()).getSupportActionBar().show();
        binding = null;

    }
    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private ScheduleDataBase db;
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
            db= ScheduleDataBaseSingleton.getInstance(null);

        }

        @Override
        protected Integer doInBackground(Void... params) {


            Date date =new Date();
            System.out.println("date");
            System.out.println(date);
            //date = new Date(date.getYear(),date.getMonth(),date.getDay());
            System.out.println(date);

            ScheduleDBDao scheduleDBDao = db.ScheduleDBDao();


            i=0;
            System.out.println("get");
            List<ScheduleDB> atList = scheduleDBDao.getAll();
            System.out.println("got");
            for (ScheduleDB at: atList) {
                System.out.println("eventflag");
                System.out.println(at.getStart().getTime()+":"+date.getTime());
                System.out.println(at.getEnd().getTime()+":"+date.getTime());
                if(at.getStart().getTime()<=date.getTime()&&date.getTime()<=at.getEnd().getTime()+24*60*60*1000){
                    list.add(at.getName());
                    System.out.println("event:"+at.getName());
                }


                i++;
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
            TodayAdapter todayAdapter = new TodayAdapter(list);
            //scheduleAdapter.setHasStableIds(true);


            recyclerView.setAdapter(todayAdapter);
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
