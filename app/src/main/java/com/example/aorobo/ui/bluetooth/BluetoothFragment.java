package com.example.aorobo.ui.bluetooth;

import android.app.Activity;
import android.os.AsyncTask;
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
import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentBluetoothBinding;
import com.example.aorobo.db.FavorabilityDataBaseSingleton;
import com.example.aorobo.db.StudyTimeDataBase;
import com.example.aorobo.db.favorability.Favorability;
import com.example.aorobo.db.favorability.FavorabilityDao;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

import android.content.Context;

import android.widget.Button;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattService;

import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.BluetoothLeScanner;

import com.example.aorobo.db.FavorabilityDataBase;
import com.example.aorobo.db.FavorabilityDataBaseSingleton;
import com.example.aorobo.ui.talk.TalkFragment;


public class BluetoothFragment extends Fragment {

    private Context context;
    private FragmentBluetoothBinding binding;
    private BluetoothAdapter bluetoothadapter;
    private BluetoothManager bluetoothmanager;
    private Set<ScanResult> mResults = new HashSet<>();
    private BluetoothLeScanner blescanner;
    private BluetoothDevice device;
    private BluetoothGattCharacteristic favo;
    private Button scanButton;
    private TextView statustxt;
    private DataStoreAsyncTask AsyncTask;

    private void Charawrite(int service_uuid,int chara_uuid,int value){
        byte[] write={(byte)value};
        BluetoothGattCharacteristic Chara=GattSingleton.getInstance(context).getService(UUID.fromString(getResources().getString(service_uuid))).getCharacteristic(UUID.fromString(getResources().getString(chara_uuid)));
        Chara.setValue(write);
        GattSingleton.getInstance(context).writeCharacteristic(Chara);
    }
    private BluetoothGattCallback gattcallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            System.out.println("connection state change!");
            System.out.print(status);
            System.out.print("->");
            System.out.println(newState);
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                dc_gatt();
            }
            else if(newState == BluetoothProfile.STATE_CONNECTED){
                GattSingleton.getInstance(context).discoverServices();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status){
            if(BluetoothGatt.GATT_SUCCESS !=status){
                System.out.println("gatt_failed");
            }
            else {
                for (BluetoothGattService service : gatt.getServices()) {
                    System.out.println(service);
                    System.out.println(service.getUuid());
                    continue;
                }

            }
            /*
            favo= gatt.getService(UUID.fromString(getResources().getString(R.string.SERVICE_UUID))).getCharacteristic(UUID.fromString(getResources().getString(R.string.favo_Chara_UUID)));
            byte[] write = {60};
            favo.setValue(write);
            gatt.writeCharacteristic(favo);
            */
        }

    } ;
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
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        return builder.build();
    }
    private ScanCallback callback =new ScanCallback(){
        @Override
        public void onScanResult(int callbackType,ScanResult result){
            System.out.println("success");
            blescanner.stopScan(callback);
            blescanner=null;
            if(callbackType == ScanSettings.CALLBACK_TYPE_ALL_MATCHES){
                device=result.getDevice();
                connect_gatt();
            }
        }


        @Override
        public void onScanFailed(int ErrorCode){
            System.out.println(ErrorCode);
            System.out.println("failed");
        }

    };

    private void connect_gatt(){
            context = getActivity();
            GattSingleton.setGatt(device.connectGatt(context, false, gattcallback, BluetoothDevice.TRANSPORT_LE));
            Charawrite(R.string.SERVICE_UUID,R.string.favo_Chara_UUID,(int)AsyncTask.favorability);
            statustxt.setText("ころボに接続済みです！");
            scanButton.setText("ころボから切断");
    }
    private void dc_gatt(){
            GattSingleton.getInstance(context).close();
            GattSingleton.setGatt(null);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBluetoothBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        AsyncTask= new DataStoreAsyncTask();
        AsyncTask.execute();
        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        scanButton=getActivity().findViewById(R.id.button);
        statustxt=getActivity().findViewById(R.id.bluetooth_status);
        if(GattSingleton.getInstance(context)!=null){
            statustxt.setText("ころボに接続済みです！");
            scanButton.setText("ころボから切断");
        }
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothmanager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
                bluetoothadapter = bluetoothmanager.getAdapter();
                //Bluetoothのon/off確認
                if(GattSingleton.getInstance(context)==null) {
                    if(blescanner==null) {
                        if (bluetoothadapter == null || !bluetoothadapter.isEnabled()) {
                            System.out.println("bluetooth is not enabled");
                            statustxt.setText("接続に失敗しました(´・ω・`)");
                        } else {
                            blescanner = bluetoothadapter.getBluetoothLeScanner();
                            //System.out.println(blescanner.size());
                            //System.out.println(blescanner);
                            blescanner.startScan(buildScanFilters(), buildScanSettings(), callback);
                            statustxt.setText("ころボを探しています！");
                            scanButton.setText("キャンセル");
                        }
                    }
                    else{
                        blescanner.stopScan(callback);
                        blescanner=null;
                        statustxt.setText(R.string.bluetooth_setting);
                        scanButton.setText("ころボを探す");
                    }
                }
                else{
                    dc_gatt();
                    statustxt.setText(R.string.bluetooth_setting);
                    scanButton.setText("ころボを探す");
                }
            }
        });
    }

    @Override
    public void onStop(){
        super.onStop();
        if(blescanner!=null) {
            blescanner.stopScan(callback);
            blescanner=null;
        }
        scanButton.setOnClickListener(null);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        long s;
        long i;
        long favorability=50;
        private FavorabilityDataBase fdb;

        @Override
        protected Integer doInBackground(Void... params) {
            System.out.println("doInBackground");
            fdb = FavorabilityDataBaseSingleton.getInstance(null);
            FavorabilityDao favorabilityDao = fdb.FavorabilityDao();
            i = 0;
            System.out.println("get");
            List<Favorability> atList = favorabilityDao.getAll();
            System.out.println("got");
            for (Favorability at : atList) {
                System.out.println(i);
                favorability += at.getfavorability();
                favorability = Math.max(favorability, 0);
                favorability = Math.min(favorability,100);
                i++;
            }
            System.out.print("favorability:");
            System.out.println(favorability);
            return 0;
        }
    }
}

