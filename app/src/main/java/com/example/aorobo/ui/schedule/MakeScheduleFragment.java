package com.example.aorobo.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.aorobo.R;

public class MakeScheduleFragment extends Fragment{


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("createview");


        return inflater.inflate(R.layout.fragment_make_schedule,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("viewcreated");
        Bundle args = getArguments();




        Button button02 = view.findViewById(R.id.savebutton);
        /*
        button02.setOnClickListener( v -> {
            FragmentManager fragmentManager = getFragmentManager();
            if(fragmentManager != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // BackStackを設定
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.replace(R.id.container, ScheduleFragment.newInstance());
                fragmentTransaction.commit();
            }
        });

         */
        /*
        // BackStackで１つ戻す
        Button pop02 = view.findViewById(R.id.pop_02);
        pop02.setOnClickListener( v -> {
            FragmentManager fragmentManager = getFragmentManager();
            if(fragmentManager != null) {
                fragmentManager.popBackStack();
            }
        });

         */
    }

}
