package colob.example.aorobo.ui.bluetooth;

import android.content.Context;

import android.bluetooth.BluetoothGatt;

final public class GattSingleton {
    private static BluetoothGatt gatt = null;

    public static void setGatt(BluetoothGatt newgatt){
        gatt=newgatt;
    }

    public static BluetoothGatt getInstance(Context context){
        return gatt;
    }
}
