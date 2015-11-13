package com.circuitos.k9.carro;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class Car extends Activity {

    public static final int REQUEST_ENABLE_BT= 9; //Request Code Parameter for bluetooth
    private static final UUID MY_UUID = null;

    private ArrayAdapter<String> mArrayAdapter;
    private ListView mListView;
    private ArrayList<String> names;
    private ArrayList<Drawable> images;
    private ArrayList<String> addresses;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

    }

   private Drawable getDrawableByMajorClass(int major) {
        Drawable drawable = null;
        switch (major) {
            case BluetoothClass.Device.Major.COMPUTER:
                drawable = getResources().getDrawable(R.drawable.black_bluetooth);
                break;
            case BluetoothClass.Device.Major.PHONE:
                drawable = getResources().getDrawable(R.drawable.black_bluetooth);
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.black_bluetooth);
                break;
        }
        return drawable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btn_warning(View view) {
        //Mandarle al bluetooth un indicador
        Toast.makeText(this, "Direccionales encendidas!", Toast.LENGTH_SHORT).show();
    }

    // Create a BroadcastReceiver for ACTION_FOUND
   public final  BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };

    public void btn_bluetooth(View view) {

     //   Toast.makeText(this, "Has seleccionado el bluetooth!", Toast.LENGTH_SHORT).show();
       //Bluetooth_helper BH =  new Bluetooth_helper();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {        // Device does not support Bluetooth
            Toast.makeText(this, "El modulo de bluetooth no es compatble con este modelo!", Toast.LENGTH_SHORT).show();
        }
        if (!mBluetoothAdapter.isEnabled()) { //Bluetooth is enabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
         //   Toast.makeText(this, "Has seleccionado el bluetooth!", Toast.LENGTH_SHORT).show();
        }

    /*Optionally, your application can also listen for the ACTION_STATE_CHANGED broadcast Intent, which the system will broadcast whenever the Bluetooth state has changed.
     This broadcast contains the extra fields EXTRA_STATE and EXTRA_PREVIOUS_STATE, containing the new and old Bluetooth states, respectively.
     Possible values for these extra fields are STATE_TURNING_ON, STATE_ON, STATE_TURNING_OFF, and STATE_OFF.
     Listening for this broadcast can be useful to detect changes made to the Bluetooth state while your app is running.*/

/*
     //   Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice blue = null;
        String suss = blue.getName();
        blue.getAddress();

        /*
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            String suss = device.getName();
            Toast.makeText(this, suss , Toast.LENGTH_SHORT).show();
        }
        // Loop through paired devices

        /*for (BluetoothDevice device : pairedDevices) {
            // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
          //  }
        }*/


        // Register the BroadcastReceiver
  //      IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    //    registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy


// Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        Toast.makeText(this, "SUCCESS!!" , Toast.LENGTH_SHORT).show();

   //     ConnectThread cnt = new ConnectThread(device);
    }

    public void btn_up (View view) {
        //Send msg to Arduino
        Toast.makeText(this, "Mover el carro hacia arriba!", Toast.LENGTH_SHORT).show();
    }


    public void btn_down(View view) {
        Toast.makeText(this, "Mover el carro hacia abajo!", Toast.LENGTH_SHORT).show();
    }


    public void btn_left(View view) {
        Toast.makeText(this, "Mover el carro hacia la izquierda", Toast.LENGTH_SHORT).show();
    }


    public void btn_right(View view) {
        Toast.makeText(this, "Mover el carro hacia la derecha!", Toast.LENGTH_SHORT).show();
    }


    public void btn_pause(View view) {
        Toast.makeText(this, "Detener el carro!", Toast.LENGTH_SHORT).show();
    }


    public void btn_go(View view) {
        Toast.makeText(this, "Avanzar el carro!", Toast.LENGTH_SHORT).show();
    }

}
