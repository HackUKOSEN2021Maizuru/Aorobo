package com.example.aorobo.ui.chart;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentBluetoothBinding;
import com.example.aorobo.databinding.FragmentChartBinding;
import com.example.aorobo.databinding.FragmentGalleryBinding;
import com.example.aorobo.ui.gallery.GalleryViewModel;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import android.content.Intent;
import android.content.Context;

public class ChartFragment extends Fragment {

    private FragmentChartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentChartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
