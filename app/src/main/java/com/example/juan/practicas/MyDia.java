package com.example.juan.practicas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MyDia extends AppCompatActivity {
    private Button borrarhistoria;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyDia.this, MenuOpciones.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_dia);

        ClientesSQLiteHelper mibd = new ClientesSQLiteHelper(MyDia.this,"Baseclientes",null,1);
        final SQLiteDatabase db =mibd.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM midia", null);

        final int cantidad = c.getCount();
        int i =0;
        final String [] arreglo =  new String[cantidad];

        if(c.moveToFirst() == true){
            do {

                String linea = c.getString(0)+": $"+c.getString(1);
                arreglo[i] = linea;
                i++;

            }while (c.moveToNext());

        }else {
            Toast.makeText(MyDia.this,"Nada registrado", Toast.LENGTH_SHORT).show();
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo);
        final ListView lista = (ListView)findViewById(R.id.milista);
        lista.setAdapter(adapter);

        borrarhistoria = (Button)findViewById(R.id.btnborrarhistoria);
        borrarhistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ventanacliente = new AlertDialog.Builder(MyDia.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                ventanacliente.setMessage("Se eliminara la historia de su dis ¿Desea continuar?");
                ventanacliente.setTitle("ELIMINAR HISTORIA");

                final EditText txtpincliente = new EditText(MyDia.this);

                txtpincliente.setWidth(100);
                txtpincliente.setHint("Escriba su PIN");
                //CENTRAR
                txtpincliente.setGravity(Gravity.CENTER);
                //SELECCIONAR EL INPUTTYPE PARA NUESTO EDITEXT
                txtpincliente.setInputType(InputType.TYPE_CLASS_NUMBER);
                //OCULTA EL CONTENIDO DE NUESTRO EDITEXT COMO UNA CONTRASEÑA
                txtpincliente.setTransformationMethod(PasswordTransformationMethod.getInstance());

                ventanacliente.setView(txtpincliente);

                ventanacliente.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences pref = getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);
                        String pinapp = pref.getString("pin","1");
                        if (pinapp.equals(txtpincliente.getText().toString())){

                            db.delete("midia",null,null);

                            Toast.makeText(MyDia.this,"Historia Eliminada",Toast.LENGTH_SHORT).show();


                        }else{
                            Toast.makeText(MyDia.this,"ERROR, pin incorrecto",Toast.LENGTH_SHORT).show();
                        }


                    }

                });
                ventanacliente.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ventanacliente.show();
            }
        });

    }
}
