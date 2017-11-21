package com.example.juan.practicas;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import objetos.Cliente;

public class OpcionesAvazadas extends AppCompatActivity {

    private Button CANCELAR_ABONO, CANCELAR_CARGO, DEVOLUCION,BORRAR_CLIENTE;
    private EditText MONTO_DEVOLUCION;
    String nombre,DEUDA_C, ABONO_C, CARGO_C;
    int CORRECCION_DEUDA, CORRECCION_DEUDA2, ABONO_CONVERTIDO, CARGO_CONVERTIDO;
    ClientesSQLiteHelper mibd = new ClientesSQLiteHelper(OpcionesAvazadas.this,"Baseclientes",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cliente_extra);

        //Asociando los objetos java con los objetos xml
        CANCELAR_ABONO = (Button)findViewById(R.id.btncancelarabono);
        CANCELAR_CARGO = (Button)findViewById(R.id.btncancelarcargo);
        DEVOLUCION = (Button)findViewById(R.id.btndevolucin);
        MONTO_DEVOLUCION = (EditText)findViewById(R.id.edtdevolucion);

        //obteniendo el bundle de la actividad pasada
        Bundle bundle = this.getIntent().getExtras();
        nombre = bundle.getString("NOMBRE2");

        //obeniendo los valores necesarios de firebase
        MenuOpciones.REFERENCIACLIENTE.child(nombre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Cliente cliente = dataSnapshot.getValue(Cliente.class);
                DEUDA_C = cliente.getAdeudo();
                ABONO_C = cliente.getUltimoPago();
                CARGO_C = cliente.getUltimoCargo();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //CANCELAR ABONO DEL CLIENTE SELECCIONADO
        CANCELAR_ABONO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ventanacliente = new AlertDialog.Builder(OpcionesAvazadas.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                ventanacliente.setMessage("Se cancelará totalmente el ultimo abono, ¿Esta seguro que desea continuar?");
                ventanacliente.setTitle("CANCELAR ABONO");

                final EditText txtpincliente = new EditText(OpcionesAvazadas.this);

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

                            SQLiteDatabase db = mibd.getWritableDatabase();
                            Cursor c = db.rawQuery("SELECT * FROM abonos WHERE nombre='"+nombre+"'", null);
                            if (c.moveToFirst() == true){

                                String abono_M= c.getString(1);
                                String fecha_M = c.getString(2);
                                //OPERACIONES PARA ANULAR EL ABONO
                                CORRECCION_DEUDA = Integer.parseInt(DEUDA_C);
                                ABONO_CONVERTIDO = Integer.parseInt(ABONO_C);
                                CORRECCION_DEUDA = CORRECCION_DEUDA + ABONO_CONVERTIDO;

                                //CONVIRTIENDO LA DEUDA A CADENA
                                String CORRECCION_DEUDA_C = Integer.toString(CORRECCION_DEUDA);

                                MenuOpciones.REFERENCIACLIENTE.child(nombre).child("adeudo").setValue(CORRECCION_DEUDA_C);
                                MenuOpciones.REFERENCIACLIENTE.child(nombre).child("ultimoPago").setValue(abono_M);
                                MenuOpciones.REFERENCIACLIENTE.child(nombre).child("fechaPago").setValue(fecha_M);

                                db.delete("abonos","nombre='"+nombre+"'",null);
                                db.close();
                                Toast.makeText(OpcionesAvazadas.this,"ABONO CANCELADO",Toast.LENGTH_LONG).show();


                            }else {
                                Toast.makeText(OpcionesAvazadas.this,"NO se puede realizar en este momento, el cliente no tiene datos en memoria",Toast.LENGTH_LONG).show();
                                db.close();
                            }

                        }else{
                            Toast.makeText(OpcionesAvazadas.this,"ERROR, pin incorrecto",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ventanacliente.show();
            }
        });
        //BOTON PARA CANCELAR COMPLETAMENTE UN CARGO REALIZADO
        CANCELAR_CARGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ventanacliente = new AlertDialog.Builder(OpcionesAvazadas.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                ventanacliente.setMessage("Se cancelará totalmente el ultimo cargo, ¿Esta seguro que desea continuar?");
                ventanacliente.setTitle("CANCELAR CARGO");

                final EditText txtpincliente = new EditText(OpcionesAvazadas.this);

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

                            SQLiteDatabase db = mibd.getWritableDatabase();

                            Cursor c = db.rawQuery("SELECT * FROM cargos WHERE nombre='"+nombre+"'", null);
                            if (c.moveToFirst() == true){

                                String cargo_M= c.getString(1);
                                String fecha_M = c.getString(2);
                                //OPERACIONES PARA ANULAR EL ABONO
                                CORRECCION_DEUDA2 = Integer.parseInt(DEUDA_C);
                                CARGO_CONVERTIDO = Integer.parseInt(CARGO_C);
                                CORRECCION_DEUDA2 = CORRECCION_DEUDA2 - CARGO_CONVERTIDO;

                                //CONVIRTIENDO LA DEUDA A CADENA
                                String CORRECCION_DEUDA_C = Integer.toString(CORRECCION_DEUDA2);

                                MenuOpciones.REFERENCIACLIENTE.child(nombre).child("adeudo").setValue(CORRECCION_DEUDA_C);
                                MenuOpciones.REFERENCIACLIENTE.child(nombre).child("ultimoCargo").setValue(cargo_M);
                                MenuOpciones.REFERENCIACLIENTE.child(nombre).child("fechaCargo").setValue(fecha_M);

                                db.delete("cargos","nombre='"+nombre+"'",null);
                                db.close();
                                Toast.makeText(OpcionesAvazadas.this,"CARGO CANCELADO",Toast.LENGTH_SHORT).show();


                            }else {
                                Toast.makeText(OpcionesAvazadas.this,"No se puede realizar en estos monentos, el cliente no tiene datos en memoria",Toast.LENGTH_LONG).show();
                                db.close();
                            }

                        }else{
                            Toast.makeText(OpcionesAvazadas.this,"ERROR, pin incorrecto",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                ventanacliente.show();
            }
        });


        //DEVOLUCION PARCIAL DEL CARGO
        DEVOLUCION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ventanacliente = new AlertDialog.Builder(OpcionesAvazadas.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                ventanacliente.setMessage("Se realizará una devolucion de $"+MONTO_DEVOLUCION.getText().toString()+" al último cargo realizado ¿Desea continuar?");
                ventanacliente.setTitle("DEVOLUCIÓN");

                final EditText txtpincliente = new EditText(OpcionesAvazadas.this);

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

                            SQLiteDatabase db = mibd.getWritableDatabase();

                            Cursor c = db.rawQuery("SELECT * FROM cargos WHERE nombre='"+nombre+"'", null);
                            if (c.moveToFirst() == true) {

                                if (MONTO_DEVOLUCION.getText().length() != 0){
                                    int devolucion = Integer.parseInt(MONTO_DEVOLUCION.getText().toString());
                                    int cargoactual = Integer.parseInt(CARGO_C);
                                    int deudaactual = Integer.parseInt(DEUDA_C);
                                    if (devolucion >= cargoactual){
                                        Toast.makeText(OpcionesAvazadas.this,"ERROR la devolucioó es mayor o igual al ultimo cargo",Toast.LENGTH_LONG).show();
                                        Toast.makeText(OpcionesAvazadas.this,"Si realizara una devolucion total se recomienda utilizar la opción, cancelar cargo",Toast.LENGTH_LONG).show();
                                    }else {
                                        //AQUI EL CODIGO DE LA DEVOLUCION
                                        cargoactual = cargoactual-devolucion;
                                        deudaactual = deudaactual-devolucion;
                                        String CA = Integer.toString(cargoactual);
                                        String DA = Integer.toString(deudaactual);

                                        MenuOpciones.REFERENCIACLIENTE.child(nombre).child("adeudo").setValue(DA);
                                        MenuOpciones.REFERENCIACLIENTE.child(nombre).child("ultimoCargo").setValue(CA);

                                        Toast.makeText(OpcionesAvazadas.this,"DEVOLUCION REALIZADA",Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    Toast.makeText(OpcionesAvazadas.this,"Debe introducir el monto de la devolucion para realizar esta operacopm",
                                            Toast.LENGTH_LONG).show();
                                }

                            }else {
                                Toast.makeText(OpcionesAvazadas.this,"ERROR, no se puede realizar esta operacion en estos momentos",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(OpcionesAvazadas.this,"ERROR, pin incorrecto",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ventanacliente.show();
            }
        });


    }
}
