package com.circuitos.k9.carro;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Set;
import java.util.UUID;

public class Carro extends AppCompatActivity{

        BluetoothAdapter mBluetoothAdapter = null;
        public final String TAG = "Carro";
        public static final int REQUEST_ENABLE_BT = 2;
        String syslog, s_ID;
        public static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
        public static final String NAME = "Carro";
        public boolean connection_is_ready = false, right_light_on = false, left_light_on = false, warning_lights_on = false;
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
            sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                right_light_on = (isChecked) ? true : false;
                if(right_light_on) {
                    if (left_light_on) {
                        sw1.setChecked(false);
                        syslog = "Recuerda apagar la direccional izquierda antes";
                        toast_log(syslog);
                    } else {
                        syslog = (connection_is_ready) ? "Direccional derecha encendida" : getString(R.string.not_connected);
                      toast_log(syslog);
                        if (connection_is_ready) send_signal('h');
                    //    toast_log(Integer.toString('h'));
                    }
                }
                else {
                    syslog = "Direccional derecha apagada";
                    toast_log(syslog);
                    if (connection_is_ready) send_signal('k');
                    //toast_log(Integer.toString('k'));
                }
            }});


            sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                left_light_on = (isChecked) ? true : false;
                if(left_light_on) {
                    if (right_light_on) {
                        sw2.setChecked(false);
                        syslog = "Recuerda apagar la direccional izquierda antes";
                        toast_log(syslog);
                    } else {
                        syslog = (connection_is_ready) ? "Direccional izquierda encendida" : getString(R.string.not_connected);
                         toast_log(syslog);
                        if (connection_is_ready) send_signal('i');
                        //toast_log(Integer.toString('i'));
                    }
                }
                else {
                    syslog = "Direccional izquierda apagada";
                    toast_log(syslog);
                    //toast_log(Integer.toString('l'));
                    if (connection_is_ready) send_signal('l');
                }
            }});

            bt = new Bluetooth(this, mHandler);
            connectService();
            connection_is_ready = true;
        }


        /**
         * Send arduino messages to move the car
         *
         * SIMBOLOGY:
         * Arrow__up    ----> 97 -----> 'a'
         * Arrow_right  ----> 98 -----> 'b'
         * Arrow_left   ----> 99 -----> 'c'
         * Arrow_down   ----> 100 ----> 'd'
         * Play         ----> 101 ----> 'e'   [batch]
         * Pause        ----> 102 ----> 'f'
         * Warning      ----> 103 ----> 'g'
         * Right_light  ----> 104 ----> 'h'
         * Left_light   ----> 105 ----> 'i'
         * Eraser        ----> 106 ----> 'j'   [borrar_estados]
         * Turn_Right_light_off ---> 107 --> 'k'
         * Turn_Left_light_off  ---> 108 --> 'l'
         * Turn_Warnings_off  ----> 109 ---> 'm'
         */

        public void btnSignal(View view) {
            switch (view.getId()) {
                case R.id.arrow_up:
                    syslog = (connection_is_ready) ? "Mover el carro hacia arriba" : getString(R.string.not_connected);
                    toast_log(syslog);
                    //toast_log(Integer.toString('a'));
                    if (connection_is_ready) send_signal('a');
                    break;
                case R.id.arrow_right:
                    syslog = (connection_is_ready) ? "Mover el carro hacia la derecha" : getString(R.string.not_connected);
                    toast_log(syslog);
                    //toast_log(Integer.toString('b'));
                    if (connection_is_ready) send_signal('b');
                    break;
                case R.id.arrow_left:
                    syslog = (connection_is_ready) ? "Mover el carro hacia la izquierda" : getString(R.string.not_connected);
                    toast_log(syslog);
                    //toast_log(Integer.toString('c'));
                    if (connection_is_ready) send_signal('c');
                    break;
                case R.id.arrow_down:
                    syslog = (connection_is_ready) ? "Mover el carro hacia abajo" : getString(R.string.not_connected);
                    toast_log(syslog);
                    //toast_log(Integer.toString('d'));
                    if (connection_is_ready) send_signal('d');
                    break;
                case R.id.play:
                    syslog = (connection_is_ready) ? "Modo Batch Activado" : getString(R.string.not_connected);
                    toast_log(syslog);
                    //toast_log(Integer.toString('e'));
                    if (connection_is_ready) send_signal('e');
                    break;
                case R.id.pause:
                    syslog = (connection_is_ready) ? "Detener el carro" : getString(R.string.not_connected);
                    toast_log(syslog);
                    //toast_log(Integer.toString('f'));
                    if (connection_is_ready) send_signal('f');
                    break;
                case R.id.warning:
                    if(!warning_lights_on) {
                        syslog = (connection_is_ready) ? "Direccionales encendidas" : getString(R.string.not_connected);
                       toast_log(syslog);
                        warning_lights_on = true;
                       // toast_log(Integer.toString('g'));
                        if (connection_is_ready) send_signal('g');
                        break;
                    } else {
                        syslog = (connection_is_ready) ? "Direccionales apagadas" : getString(R.string.not_connected);
                        toast_log(syslog);
                        warning_lights_on = false;
                        if (connection_is_ready) send_signal('m');
                        //toast_log(Integer.toString('m'));
                        break;
                    }
                case R.id.freno:
                    syslog = (connection_is_ready) ? "Estados Borrados" : getString(R.string.not_connected);
                    toast_log(syslog);
                   // toast_log(Integer.toString('j'));
                    if(connection_is_ready) send_signal('j');
                    break;
                 default:
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                    break;
            }
        }


        /**
         * This code will check to see if there is a bluetooth device and turn it on if is it turned off.
         */

        public void start_bt(View view) {
         /*   mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
            }*/
         connectService();
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

    public void connectService(){
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter.isEnabled()) {
                bt.start();
                bt.connectDevice("HC-06");
                Log.d(TAG, "Btservice started - listening");
            } else {
                Log.w(TAG, "Btservice started - bluetooth is not enabled");
            }
        } catch(Exception e){
            Log.e(TAG, "Unable to start bt ",e);
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
