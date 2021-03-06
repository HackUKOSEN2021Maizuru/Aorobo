package com.example.aorobo.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentHomeBinding;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.StudyTimeDataBaseSingleton;
import com.example.aorobo.db.schedule.ScheduleDB;
import com.example.aorobo.db.schedule.ScheduleDBDao;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.db.time.TimeDBDao;
import com.example.aorobo.ui.time.TimeFragment;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;



public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private StudyTimeDataBase db;
    private ScheduleDataBase sdb;
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

        Bundle args = getArguments();
        //ListView listView=getActivity().findViewById(R.id.listViewicon);
        sdb = ScheduleDataBaseSingleton.getInstance(null);







        timeText=getActivity().findViewById(R.id.study_time);

        RecyclerView recyclerView = getActivity().findViewById(R.id.home_schedule_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView recyclerViewIcon= getActivity().findViewById(R.id.home_schedule_recycler_view);
        recyclerViewIcon.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(rLayoutManager);

        RecyclerView.LayoutManager riconLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerViewIcon.setLayoutManager(riconLayoutManager);

        new DataStoreAsyncTask(db, getActivity(), timeText,0,sdb,recyclerView,view.getContext()).execute();


        BluetoothAdapter mBluetoothAdapter;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // ?????????: Bluetooth ??????.
            System.out.println("no bluetooth!!");
        }else{
            System.out.println("yes bluetooth!!");
        }


        // BackStack???????????????
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
        static List<String> iPeriod = new ArrayList<String>();
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

        @RequiresApi(api = Build.VERSION_CODES.N)
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
            System.out.println("?????????????????????");
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
            sList.sort(Comparator.comparing(ScheduleDB::getStart));
            System.out.println("got");
            iName.clear();
            iDate.clear();
            icnt.clear();
            iPeriod.clear();
            date=new Date();
            System.out.println("fafdsaffsfsdf");
            icnt.add("");
            icnt.add("");
            List<ScheduleDB> sList2=new ArrayList<ScheduleDB>();
            int x=0,y=0;
            for (ScheduleDB at: sList){
                if(at.getEnd().getTime()+24*60*60*1000<date.getTime()){
                    scheduleDBDao.delete(at.getId());
                    y++;
                    continue;
                }
                else if(at.getStart().getTime()<date.getTime()){
                    sList2.add(at);
                    x++;
                }

            }
            sList2.sort(Comparator.comparing(ScheduleDB::getEnd));
            for(int i=0;i<x;i++){
                ScheduleDB at=sList2.get(i);
                String st = new SimpleDateFormat("yyyy.MM.dd").format(at.getStart());
                String en = new SimpleDateFormat("yyyy.MM.dd").format(at.getEnd());

                //iPeriod.add(String.format(Locale.US, "%1$04d.%2$02d.%3$02d~%4$04d.%5$02d.%6$02d", at.getStart().getYear(),at.getStart().getMonth(),at.getStart().getDay(),at.getEnd().getYear(),at.getEnd().getMonth(),at.getEnd().getDay()));

                iPeriod.add(st+"~"+en);
                long t=(at.getEnd().getTime()-date.getTime())/1000/60/60/24+1;
                iName.add(at.getName());
                if(at.getEnd().getTime()<date.getTime()){
                    iDate.add("TODAY!");
                }else{
                    iDate.add(String.format(Locale.US, " ??????%1$02d???", t));
                }

                //data.put("time",String.format(Locale.US, "??????%1$02d???", t));

                System.out.println(at.getName());
                System.out.println(at.getStart());
                System.out.println(at.getEnd());
                System.out.println(date);
                System.out.println((at.getEnd().getTime()-date.getTime())/1000/60/60/24);


            }

            sList2.sort(Comparator.comparing(ScheduleDB::getEnd));
            for(int i=x+y;i<sList.size();i++){
                ScheduleDB at=sList.get(i);
                String st = new SimpleDateFormat("yyyy.MM.dd").format(at.getStart());
                String en = new SimpleDateFormat("yyyy.MM.dd").format(at.getEnd());

                //iPeriod.add(String.format(Locale.US, "%1$04d.%2$02d.%3$02d~%4$04d.%5$02d.%6$02d", at.getStart().getYear(),at.getStart().getMonth(),at.getStart().getDay(),at.getEnd().getYear(),at.getEnd().getMonth(),at.getEnd().getDay()));

                iPeriod.add(st+"~"+en);
                long t=(at.getStart().getTime()-date.getTime())/1000/60/60/24+1;
                iName.add(at.getName());
                if(at.getEnd().getTime()<date.getTime()){
                    iDate.add("TODAY!");
                }else{
                    iDate.add(String.format(Locale.US, " %1$02d??????", t));
                }

                //data.put("time",String.format(Locale.US, "??????%1$02d???", t));

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

            HomeAdapter homeAdapter = new HomeAdapter(iName,iDate,iPeriod);
            recyclerView.setAdapter(homeAdapter);
            //int x=R.id.imageViewhomeicon0;
            List<Integer> iconlist=new ArrayList<Integer>();
            iconlist.add(R.id.imageViewhomeicon0);
            iconlist.add(R.id.imageViewhomeicon1);
            iconlist.add(R.id.imageViewhomeicon2);
            iconlist.add(R.id.imageViewhomeicon3);
            iconlist.add(R.id.imageViewhomeicon4);

            LinearLayout linearLayout=activity.findViewById(R.id.study_lay);
            System.out.println("width"+linearLayout.getWidth());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(linearLayout.getWidth()/5-50,linearLayout.getWidth()/5-50);
            System.out.println("Time::s::"+times/10/60);
            for(int i=0;i<Math.min(5,times/10/60/60);i++){
                ImageView imageViewicon = activity.findViewById(iconlist.get(i));

                //MaxHeight(linearLayout.getWidth()/5-50);

                imageViewicon.setImageResource(R.drawable.one_colobo);
                System.out.println("icon"+imageViewicon.getWidth());
                //imageViewicon.setLayoutParams(params);
                //imageViewicon.setMaxWidth(R.id.study_lay/5-50);
            }

            //recyclerViewIcon.setAdapter(new HomeStudyIconAdapter(icnt));

            /*
            adapter = new SimpleAdapter(
                    context,
                    icnt,
                    R.layout.row_home,
                    new String [] {"name", "time"},
                    new int[] {R.id.schedule_name_home, R.id.textView5}
            );
             */
            //listView=

            //listView.setAdapter(new ArrayAdapter<String>(context, R.layout.raw_icon, icnt));
            System.out.println("c");

        }
        public long getTimes(){
            System.out.println("ans:");
            System.out.println(times);
            return times;
        }
    }

}