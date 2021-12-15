package com.example.aorobo.ui.talk;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.ui.schedule.ScheduleFragment;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

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
        loadImage(Glide.with(this), R.raw.colob_roll, getActivity().findViewById(R.id.gifView));
        //Glide.with(this.getContext()).load(R.raw.colob_roll).into(imageView);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}
