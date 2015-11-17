package com.circuitos.k9.carro.com.circuitos.k9.carro.conection_threads;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.widget.Toast;

public class Bluetooth_helper {

    public static final int REQUEST_ENABLE_BT= 9; //Request Code Parameter for bluetooth
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice device;
    /*if (mBluetoothAdapter == null) {        // Device does not support Bluetooth_helper
        Toast.makeText(this, "El modulo de bluetooth no es compatble con este modelo!", Toast.LENGTH_SHORT).show();
    }
    if (!mBluetoothAdapter.isEnabled()) { //Bluetooth_helper is enabled
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }
*/
}