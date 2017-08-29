package com.example.juan.practicas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import objetos.FirebaseReferenses;

public class ListaClientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        //Toast de la clase
        Toast toastpincorrecto = Toast.makeText(getApplicationContext(),"Pin Correcto sesion inciada",Toast.LENGTH_SHORT);

        //Mensaje de inicio de sesion correcta
        toastpincorrecto.show();

        //Conectando con nuestra base de datos Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //haciendo referencia a los datos que queremos
        DatabaseReference refusuarios = database.getReference(FirebaseReferenses.CLIENTES_REFERENSES);
        refusuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valor = dataSnapshot.getValue().toString();
                Log.i("DATOS",valor);

                String ejemplo ="hola";
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR",databaseError.getMessage());
            }
        });

    }
}
