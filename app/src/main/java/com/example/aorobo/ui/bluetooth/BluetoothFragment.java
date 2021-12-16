package com.example.aorobo.ui.bluetooth;

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

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.BluetoothLeScanner;

public class BluetoothFragment extends Fragment{

    private FragmentBluetoothBinding binding;
    private BluetoothAdapter bluetoothadapter;
    private BluetoothManager bluetoothmanager;
    private Set<ScanResult> mResults = new HashSet<>();
    private List<ScanResult> mBatchScanResults = new ArrayList<>();
    private BluetoothLeScanner blescanner;

    private List<ScanFilter> buildScanFilters(){
        System.out.println("filters");
        List<ScanFilter> scanFilters = new ArrayList<>();
        ScanFilter.Builder builder = new ScanFilter.Builder();
        builder.setDeviceName("Corobo");
        scanFilters.add(builder.build());
        return scanFilters;
    }
    private ScanSettings buildScanSettings(){
        System.out.println("setting");
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_BALANCED);
        return builder.build();
    }
    private ScanCallback callback =new ScanCallback(){
        @Override
        public void onScanResult(int callbackType,ScanResult result){
            //System.out.println("success");
            if(callbackType == ScanSettings.CALLBACK_TYPE_ALL_MATCHES){
                mResults.add(result);
                System.out.println(mResults);
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results){
            mBatchScanResults.addAll(results);
            //System.out.println("success");
        }

        @Override
        public void onScanFailed(int ErrorCode){
            System.out.println(ErrorCode);
            //System.out.println("failed");
        }
    };
    /*
    public class BleScanCallback extends ScanCallback{
        private Set<ScanResult> mResults = new HashSet<>();
        private List<ScanResult> mBatchScanResults = new ArrayList<>();

        @Override
        public void onScanResult(int callbackType,ScanResult result){
            if(callbackType == ScanSettings.CALLBACK_TYPE_ALL_MATCHES){
                mResults.add(result);
                System.out.println("success");
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results){
            mBatchScanResults.addAll(results);
            System.out.println("success");
        }

        @Override
        public void onScanFailed(int ErrorCode){
            System.out.println(ErrorCode);
            System.out.println("failed");
        }
    }
    */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBluetoothBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //BLEセットアップ
        bluetoothmanager=(BluetoothManager)getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothadapter=bluetoothmanager.getAdapter();

        //Bluetoothのon/off確認
        if(bluetoothadapter == null || !bluetoothadapter.isEnabled()) {
            System.out.println("bluetooth is not enabled");
        }

        blescanner = bluetoothadapter.getBluetoothLeScanner();
        //System.out.println(blescanner.size());
        //System.out.println(blescanner);
        blescanner.startScan(buildScanFilters(),buildScanSettings(),callback);
        //System.out.println(blescanner.EXTRA_CALLBACK_TYPE);
        //System.out.println(blescanner.EXTRA_ERROR_CODE);
        //System.out.println(blescanner.EXTRA_LIST_SCAN_RESULT);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
