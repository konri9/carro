package com.circuitos.k9.carro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Car extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
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


    public void btn_up (View view) {
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
