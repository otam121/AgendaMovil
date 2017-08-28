package com.example.juan.practicas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables de la aplicacion
    String mipin="1234";

    //declaracion de objetos que son elementos graficos de la app
    //all editext
    private EditText edtpin;

    //all buttons
    private Button btnpin;

    //all textviews


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociando los objetos java con sus respectivos elementos del xml
        //all editext
        edtpin=(EditText)findViewById(R.id.edtPin);

        //all Buttons
        btnpin=(Button)findViewById(R.id.btnPin);

        //all texviews


        //Inicio de sesion
        btnpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //alertas de la aplicacion

                Toast toastpinvacio = Toast.makeText(getApplicationContext(),"Debe intraducir el pin antes de presionar el boton",Toast.LENGTH_SHORT);
                Toast toastpinincorrecto = Toast.makeText(getApplicationContext(),"Pin incorrecto intente nuevamente", Toast.LENGTH_SHORT);

                //variable que contiene el valor ingresado en pin por el usuario
                String pintexto = edtpin.getText().toString();

                //control del pin
                if(pintexto.equals("")){
                    toastpinvacio.show();
                }else if (pintexto.equals("1234")) {
                    //creando intento para lanzar la actividad que contiene la lista de todos los clientes
                    Intent clientes = new Intent(MainActivity.this, ListaClientes.class);
                    startActivity(clientes);
                } else {
                    toastpinincorrecto.show();
                }


            }
        });


    }
}
