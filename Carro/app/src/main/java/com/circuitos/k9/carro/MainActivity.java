package com.circuitos.k9.carro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_driver(View view)
    {
        Intent intent = new Intent(this, Carro.class);
        startActivity(intent);
    }

    public void btn_credits(View view)
    {
        Intent intent = new Intent(this,Creditos.class);
        startActivity(intent);
    }

    public void btn_clax (View view)
    {
        Toast.makeText(this, "PIIIIIIIIII PIIIIIII", Toast.LENGTH_SHORT).show();
    }

}
