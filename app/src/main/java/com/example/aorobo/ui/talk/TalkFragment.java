package com.example.aorobo.ui.talk;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentTalkBinding;
import com.example.aorobo.db.FavorabilityDataBase;
import com.example.aorobo.db.FavorabilityDataBaseSingleton;
import com.example.aorobo.db.favorability.Favorability;
import com.example.aorobo.db.favorability.FavorabilityDao;
import com.example.aorobo.ui.bluetooth.GattSingleton;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
        new DataStoreAsyncTask(getActivity(),0).execute();
        loadImage(Glide.with(getActivity()), R.raw.colob_roll, getActivity().findViewById(R.id.gifView));
        ImageView coloboView = getActivity().findViewById(R.id.gifView);
        coloboView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Bluetoothの処理
                BluetoothGatt gatt=GattSingleton.getInstance(null);
                System.out.println("got gatt");
                if(gatt!=null){
                    System.out.println("BLE connected.Send:touch");
                    Charawrite(gatt,R.string.SERVICE_UUID,R.string.touch_Chara_UUID,1);
                    System.out.println("(write)touch:1");
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Glide.with(getActivity()).onDestroy();
        Glide.with(getActivity()).clear(getView());
        binding = null;

    }
    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        long s;
        long favorability;
        private FavorabilityDataBase fdb;
        Activity activity;
        private Handler mHandler = new Handler();

        public DataStoreAsyncTask(Activity activity,long s) {
           // this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.activity=activity;
            //this.textView = textView;
            this.s=s;
            fdb = FavorabilityDataBaseSingleton.getInstance(null);


        }

        @Override
        protected Integer doInBackground(Void... params) {

            FavorabilityDao favorabilityDao=fdb.FavorabilityDao();
            //timeDBDao.nukeTable();
            Date date =new Date();

            favorability=50;

            List<Favorability> atList = favorabilityDao.getAll();
            for (Favorability at: atList) {
                favorability+=at.getfavorability();
                favorability=Math.max(favorability,0);
            }
            TextView favorabilityText=activity.findViewById(R.id.textViewFav);
            favorabilityText.setText(String.format(Locale.US, "%1$03d",favorability));

            return 0;
        }
    }
    private void Charawrite(BluetoothGatt gatt, int service_uuid, int chara_uuid, int value){
        byte[] write={(byte)value};
        BluetoothGattCharacteristic Chara=GattSingleton.getInstance(null).getService(UUID.fromString(getResources().getString(service_uuid))).getCharacteristic(UUID.fromString(getResources().getString(chara_uuid)));
        Chara.setValue(write);
        gatt.writeCharacteristic(Chara);
    }
}
