package com.example.aorobo.ui.bluetooth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.aorobo.databinding.FragmentBluetoothConnectedBinding;
import com.example.aorobo.databinding.FragmentHomeBinding;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.StudyTimeDataBaseSingleton;
import com.example.aorobo.db.TimeDatabase;
import com.example.aorobo.db.TimeDatabaseSingleton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentBluetoothBinding;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

public class BluetoothConnectedFragment extends Fragment{
    private FragmentBluetoothConnectedBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ImageView matchImage = getActivity().findViewById(R.id.gifView);
        Glide.with(this).load(R.raw.colob_roll).into(matchImage);

        binding = FragmentBluetoothConnectedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}
