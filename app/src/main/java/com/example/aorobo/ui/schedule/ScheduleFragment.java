package com.example.aorobo.ui.schedule;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentScheduleBinding;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.schedule.ScheduleDB;
import com.example.aorobo.db.schedule.ScheduleDBDao;
import com.example.aorobo.ui.slideshow.SlideshowViewModel;

import java.lang.ref.WeakReference;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment{
    private FragmentScheduleBinding binding;
    ListView listView;
    static List<String> items = new ArrayList<String>();
    static ArrayAdapter<String> adapter;
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
        listView=getActivity().findViewById(R.id.shedule_list);
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");

        adapter = new ArrayAdapter<String>(view.getContext(), R.layout.row, R.id.schedule_name, items);

        listView.setAdapter(adapter);
        db = ScheduleDataBaseSingleton.getInstance(null);



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

        public DataStoreAsyncTask(ScheduleDataBase db, Activity activity, Date startDay,Date endDay,String scheduleName) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.startDay=startDay;
            this.endDay=endDay;
            this.scheduleName=scheduleName;


        }

        @Override
        protected Integer doInBackground(Void... params) {
            ScheduleDBDao scheduleDBDao = db.ScheduleDBDao();
            //timeDBDao.nukeTable();

            scheduleDBDao.insert(new ScheduleDB(scheduleName,startDay,endDay));





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
