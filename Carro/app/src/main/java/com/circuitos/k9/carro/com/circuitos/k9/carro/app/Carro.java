package com.circuitos.k9.carro.com.circuitos.k9.carro.app;

import java.util.Set;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.circuitos.k9.carro.R;


public class Carro extends AppCompatActivity{

    BluetoothAdapter mBluetoothAdapter = null;
    public final String TAG = "Carro";
    public static final int REQUEST_ENABLE_BT = 2;
    String syslog, s_ID;
    public static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    public static final String NAME = "Carro";
    public boolean connection_is_ready = false, right_light_on, left_light_on;
    Bluetooth bt;
    Switch sw1, sw2;

    /**
     * Method helpers
     */

    public void toast_log(String log) { Toast.makeText(getApplicationContext(), log, Toast.LENGTH_LONG).show(); }

 //   public void bluetooth_adapter_set() { connection_is_ready = (mBluetoothAdapter.isEnabled()) ? true : false; }

    public void send_signal(int button_id) { bt.sendMessage(Integer.toString(button_id)); }

    public int get_id (View view){ return view.getId(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        bt = new Bluetooth(this, mHandler);
        sw1 = (Switch) findViewById(R.id.right_light);
        sw2 = (Switch) findViewById(R.id.left_light);

        //Listener to check for changes in state
        sw1.setOnCheckedChangeListener(new OnCheckedChangeListener() {@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {right_light_on = (isChecked) ? true : false;}});
        sw2.setOnCheckedChangeListener(new OnCheckedChangeListener() {@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {left_light_on = (isChecked) ? true : false;}});
    }


    /**
     * Send arduino messages to move the car
     */

    public void btnSignal(View view) {
        switch (view.getId()) {
            case R.id.arrow_up:
                syslog = (connection_is_ready) ? "Mover el carro hacia arriba" : getString(R.string.not_connected);
                toast_log(syslog);
                send_signal(get_id(view));
                break;
            case R.id.arrow_right:
                syslog = (connection_is_ready) ? "Mover el carro hacia la derecha" : getString(R.string.not_connected);
                toast_log(syslog);
                send_signal(get_id(view));
                break;
            case R.id.arrow_left:
                syslog = (connection_is_ready) ? "Mover el carro hacia la izquierda" : getString(R.string.not_connected);
                toast_log(syslog);
                send_signal(get_id(view));
                break;
            case R.id.arrow_down:
                syslog = (connection_is_ready) ? "Mover el carro hacia abajo" : getString(R.string.not_connected);
                toast_log(syslog);
                send_signal(get_id(view));
                break;
            case R.id.play:
                syslog = (connection_is_ready) ? "Avanzar el carro" : getString(R.string.not_connected);
                toast_log(syslog);
                send_signal(get_id(view));
                break;
            case R.id.pause:
                syslog = (connection_is_ready) ? "Detener el carro" : getString(R.string.not_connected);
                toast_log(syslog);
                send_signal(get_id(view));
                break;
            case R.id.warning:
                syslog = (connection_is_ready) ? "Direccionales encendidas" : getString(R.string.not_connected);
                toast_log(syslog);
                send_signal(get_id(view));
                break;
            case R.id.right_light:
                if(right_light_on) {
                    if (left_light_on) {
                        syslog = "Debes de apagar la direccional izquierda antes";
                    } else {
                        syslog = (!connection_is_ready) ? "Direccional derecha encendida" : getString(R.string.not_connected);
                        toast_log(syslog);
                        send_signal(get_id(view));
                        break;
                    }
                }
                else if(!right_light_on) syslog = "Direccional derecha apagada";
                toast_log(syslog);
                break;
            case R.id.left_light:
                if(left_light_on) {
                    if (right_light_on) {
                        syslog = "Debes de apagar la direccional izquierda antes";
                    } else {
                        syslog = (!connection_is_ready) ? "Direccional derecha encendida" : getString(R.string.not_connected);
                        toast_log(syslog);
                        send_signal(get_id(view));
                        break;
                    }
                }
                else if(!left_light_on) syslog = "Direccional derecha apagada";
                toast_log(syslog);
                break;
            case R.id.freno:
                syslog = (connection_is_ready) ? "Carro frenado" : getString(R.string.not_connected);
                toast_log(syslog);
                send_signal(get_id(view));
                break;
            default:
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                break;
        }
    }

/*
    //Metodo de prueba
    int count = 0;
    public void btn_stop_lights(View view) {
        count++;
        String cuantas_veces = Integer.toString(count);
        Toast.makeText(this, cuantas_veces, Toast.LENGTH_SHORT).show();
        if (count == 20) count = 0;
    }
*/

    /**
     * This code will check to see if there is a bluetooth device and turn it on if is it turned off.
     */

    public void start_bt(View view) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            toast_log("El modulo de bluetooth no es compatble con este modelo!");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } // llamar a bluetooth_adapter_set
        //query_paired();
        if (mBluetoothAdapter.isEnabled()){
            connection_is_ready = true;
            bt.start();
            bt.connectDevice("HC-06");
        }
    }

    // add to a ListActivity later
    /**
     * This method will query the bluetooth device and ask for a list of all
     * paired devices.  It will then display to the screen the name of the device and the address
     * In client fragment we need this address to so we can connect to the bluetooth device that is acting as the server.
     */
    public void query_paired() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            Toast.makeText(this, "DEVICES", Toast.LENGTH_SHORT).show();
            final BluetoothDevice blueDev[] = new BluetoothDevice[pairedDevices.size()];
            String item;
            int i = 0;
            for (BluetoothDevice devicel : pairedDevices) {
                blueDev[i] = devicel;
                item = blueDev[i].getName() + ": " + blueDev[i].getAddress();
                i++;
                Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:
                    Log.d(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.d(TAG, "MESSAGE_WRITE ");
                    break;
                case Bluetooth.MESSAGE_READ:
                    Log.d(TAG, "MESSAGE_READ ");
                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "MESSAGE_DEVICE_NAME "+msg);
                    break;
                case Bluetooth.MESSAGE_TOAST:
                    Log.d(TAG, "MESSAGE_TOAST "+msg);
                    break;
            }
        }
    };







}