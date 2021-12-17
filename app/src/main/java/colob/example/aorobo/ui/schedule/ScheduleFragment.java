package colob.example.aorobo.ui.schedule;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import colob.example.aorobo.databinding.FragmentScheduleBinding;
import colob.example.aorobo.db.ScheduleDataBase;
import colob.example.aorobo.db.ScheduleDataBaseSingleton;
import colob.example.aorobo.db.schedule.ScheduleDB;
import colob.example.aorobo.db.schedule.ScheduleDBDao;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import colob.example.aorobo.R;

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
    public void onResume() {
        super.onResume();
        db = ScheduleDataBaseSingleton.getInstance(null);
        //getActivity().getSu

        RecyclerView recyclerView = getActivity().findViewById(R.id.shedule_list);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getView().getContext());

        recyclerView.setLayoutManager(rLayoutManager);

        new DataStoreAsyncTask(db, getActivity(),recyclerView,getView().getContext()).execute();



        Button button02 = getView().findViewById(R.id.add_button);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_first_to_second);
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //listView=getActivity().findViewById(R.id.shedule_list);




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
        static List<String> iPeriod = new ArrayList<String>();
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
            scheduleDBDao.sort();
            //timeDBDao.nukeTable();

            List<ScheduleDB> sList = scheduleDBDao.getAll();
            sList.sort(Comparator.comparing(ScheduleDB::getEnd));
            System.out.println("got");
            iName.clear();
            iDate.clear();
            ids.clear();
            Date date=new Date();

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
                ids.add(at.getId());
                if(at.getEnd().getTime()<date.getTime()){
                    iDate.add("TODAY!");
                }else{
                    iDate.add(String.format(Locale.US, " 残り%1$02d日", t));
                }

                //data.put("time",String.format(Locale.US, "残り%1$02d日", t));

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
                long t=(at.getStart().getTime()-date.getTime())/1000/60/60/24;
                iName.add(at.getName());
                ids.add(at.getId());
                if(at.getStart().getTime()<date.getTime()){
                    iDate.add("TODAY!");
                }else{
                    iDate.add(String.format(Locale.US, " %1$02d日後", t));
                }

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
            System.out.println("a");


            System.out.println("b");
            scheduleAdapter = new ScheduleAdapter(iName,iDate,ids,iPeriod);
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
                    System.out.println("start");
                    iName.remove(position);
                    iDate.remove(position);
                    ids.remove(position);
                    iPeriod.remove(position);
                    System.out.println("end");
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
