package com.example.juan.practicas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import objetos.FirebaseReferenses;

public class ListaClientes extends AppCompatActivity {

    private Button botonprobar;

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
        final DatabaseReference refusuarios = database.getReference(FirebaseReferenses.CLIENTES_REFERENSES);

        botonprobar=(Button)findViewById(R.id.btnprueba);
        botonprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refusuarios.child("Casandra1").child("deuda").setValue(13);
            }
        });

        refusuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valor = dataSnapshot.getValue().toString();
                Log.i("DATOS",valor);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR",databaseError.getMessage());
            }
        });

        refusuarios.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
