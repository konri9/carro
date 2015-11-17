package com.circuitos.k9.carro.com.circuitos.k9.carro.app;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.circuitos.k9.carro.R;
import com.circuitos.k9.carro.com.circuitos.k9.carro.conection_threads.Bluetooth_helper;

public class Carro extends AppCompatActivity {

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
        Bluetooth_helper BH =  new Bluetooth_helper();
    }

    int count =0;
    public void btn_stop_lights(View view) {
        // PRUEBAS
        count++;
        String cuantas_veces = Integer.toString(count);
        Toast.makeText(this,cuantas_veces, Toast.LENGTH_SHORT).show();
        if (count == 20) count = 0;
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

    /**Para el modo de funcionamiento por intervalos*/

    public void btn_pause(View view) {
        Toast.makeText(this, "Detener el carro!", Toast.LENGTH_SHORT).show();
    }


    public void btn_go(View view) {
        Toast.makeText(this, "Avanzar el carro!", Toast.LENGTH_SHORT).show();
    }

}