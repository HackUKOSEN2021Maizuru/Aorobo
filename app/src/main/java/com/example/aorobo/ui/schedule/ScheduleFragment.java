package com.example.aorobo.ui.schedule;

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
import com.example.aorobo.ui.slideshow.SlideshowViewModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment{
    private FragmentScheduleBinding binding;
    ListView listView;
    static List<String> items = new ArrayList<String>();
    static ArrayAdapter<String> adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=getActivity().findViewById(R.id.listview1);
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");

        adapter = new ArrayAdapter<String>(view.getContext(), R.layout.row, R.id.schedule_name, items);

        listView.setAdapter(adapter);



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
}
