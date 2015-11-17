package com.circuitos.k9.carro;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Carro extends AppCompatActivity {

    public static final int REQUEST_ENABLE_BT= 9; //Request Code Parameter for bluetooth
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
    }

    public void btn_warning(View view) {
        //Mandarle al bluetooth un indicador
        Toast.makeText(this, "Direccionales encendidas!", Toast.LENGTH_SHORT).show();
    }

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
        }
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