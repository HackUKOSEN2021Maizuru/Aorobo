package com.example.aorobo.ui.title;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aorobo.databinding.FragmentTalkBinding;

public class TitleFragment extends Fragment{
    private FragmentTalkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTalkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}