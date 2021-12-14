package com.example.aorobo.ui.schedule;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentScheduleBinding;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.schedule.ScheduleDB;
import com.example.aorobo.db.schedule.ScheduleDBDao;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.ui.home.HomeAdapter;
import com.example.aorobo.ui.slideshow.SlideshowViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ScheduleFragment extends Fragment{
    private FragmentScheduleBinding binding;
    ListView listView;

    private ScheduleDataBase db;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //listView=getActivity().findViewById(R.id.shedule_list);

        db = ScheduleDataBaseSingleton.getInstance(null);
        //getActivity().getSu

        RecyclerView recyclerView = getActivity().findViewById(R.id.shedule_list);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(rLayoutManager);

        new DataStoreAsyncTask(db, getActivity(),recyclerView,view.getContext()).execute();



        Button button02 = view.findViewById(R.id.add_button);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_first_to_second);
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
        private ScheduleDataBase db;
        Date startDay,endDay;
        String scheduleName;
        ListView listView;
        Context context;
        RecyclerView recyclerView;
        ScheduleAdapter scheduleAdapter;
        Activity activity;



        static List<String> iName = new ArrayList<String>();
        static List<String> iDate = new ArrayList<String>();
        static List<Integer> ids = new ArrayList<Integer>();
        //static SimpleAdapter adapter;
        public DataStoreAsyncTask(ScheduleDataBase db, Activity activity, RecyclerView recyclerView, Context context) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.activity=activity;
            this.recyclerView=recyclerView;
            this.context=context;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            ScheduleDBDao scheduleDBDao = db.ScheduleDBDao();
            //timeDBDao.nukeTable();

            List<ScheduleDB> atList = scheduleDBDao.getAll();
            System.out.println("got");
            iName.clear();
            iDate.clear();
            Date date=new Date();

            for (ScheduleDB at: atList) {
                //Map<String,String> data = new HashMap();
                //data.put("name",at.getName());
                long t=(at.getEnd().getTime()-date.getTime())/1000/60/60/24;
                iName.add(at.getName());
                iDate.add(String.format(Locale.US, "残り%1$02d日", t));
                ids.add(at.getId());
                //data.put("time",String.format(Locale.US, "残り%1$02d日", t));
                System.out.println(at.getName());
                System.out.println(at.getStart());
                System.out.println(at.getEnd());
                System.out.println(date);
                System.out.println((at.getEnd().getTime()-date.getTime())/1000/60/60/24);

            }
            System.out.println("push");



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
            scheduleAdapter = new ScheduleAdapter(iName,iDate,ids);
            //scheduleAdapter.setHasStableIds(true);
            recyclerView.setAdapter(scheduleAdapter);
            listsSwipe();
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
        public void listsSwipe() {
            ItemTouchHelper.SimpleCallback mIth = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    //((ViewAdapter)recyclerView.getAdapter()).removeItem(position);
                    int id = scheduleAdapter.getId(position);

                    iName.remove(position);
                    iDate.remove(position);
                    ids.remove(position);
                    ScheduleDBDao scheduleDBDao = db.ScheduleDBDao();
                    //scheduleDBDao.delete((int)id);
                    new DataStoreAsyncTaskDelete(db,activity,(int)id).execute();
                    scheduleAdapter.notifyDataSetChanged();

                }

            };
            new ItemTouchHelper(mIth).attachToRecyclerView(recyclerView);

        }

    }

    private static class DataStoreAsyncTaskDelete extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private ScheduleDataBase db;
        int id;



        static List<String> iName = new ArrayList<String>();
        static List<String> iDate = new ArrayList<String>();
        //static SimpleAdapter adapter;
        public DataStoreAsyncTaskDelete(ScheduleDataBase db,Activity activity, int id) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.id=id;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            ScheduleDBDao scheduleDBDao = db.ScheduleDBDao();
            //timeDBDao.nukeTable();

            System.out.println("deleteid:"+id);
            scheduleDBDao.delete(id);


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
