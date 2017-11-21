package com.example.juan.practicas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Configuracion extends AppCompatActivity {

    private EditText pinactual,pinnuevo1,pinnuevo2;
    private Button btncambiopin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        //ASOCIANDO LOS OBJETOS JAVA A LOS XML
        pinactual = (EditText)findViewById(R.id.edtpinactual);
        pinnuevo1 = (EditText)findViewById(R.id.edtpin1);
        pinnuevo2 = (EditText)findViewById(R.id.edtpin2);
        btncambiopin = (Button)findViewById(R.id.btncambiarpin);

        btncambiopin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);
                String pin = pref.getString("pin","1");

                //VERIFICANDO QUE PIN1 Y PIN2 NO ESTEN VACIOS
                if (pinnuevo1.getText().length() != 0 && pinnuevo2.getText().length() != 0 && pinactual.getText().length() != 0){
                    //VERIFICAND QUE EL PIN ACTUAL SEA CORRECTO
                    if (pinactual.getText().toString().equals(pin)){

                        //VERIFICANDO QUE LOS PINS INTRODUCIDOS COINCIDAN
                        if (pinnuevo1.getText().toString().equals(pinnuevo2.getText().toString())){
                            //COMPROBANDO QUE LOS PIN TRENGAN 4 O MAS DIGIOS
                            if (pinnuevo1.getText().length() >=4 && pinnuevo2.getText().length() >=4) {

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("pin", pinnuevo1.getText().toString());
                                editor.commit();

                                MenuOpciones.REFERENCIACONFIG.child("pin").setValue(pinnuevo1.getText().toString());

                                pinactual.setText("");
                                pinnuevo1.setText("");
                                pinnuevo2.setText("");
                                Toast.makeText(Configuracion.this,"¡FELICIADADES! el pin se registro exitosamente",Toast.LENGTH_LONG).show();

                            }else {
                                pinnuevo1.setText("");
                                pinnuevo2.setText("");
                                Toast.makeText(Configuracion.this,"El pin debe contener almenos 4 digitos por su seguridad, intente nuevamente",Toast.LENGTH_LONG).show();
                            }

                        }else{
                            //LOS PINS NO COINCIDEN
                            pinnuevo1.setText("");
                            pinnuevo2.setText("");
                            Toast.makeText(Configuracion.this,"Los valores del PIN no coinciden intente una vez mas",Toast.LENGTH_LONG).show();
                        }


                    }else {
                        Toast.makeText(Configuracion.this, "El pin actual es incorrecto por favor intente nuevamente",
                                Toast.LENGTH_LONG).show();
                        pinactual.setText("");
                        pinnuevo1.setText("");
                        pinnuevo2.setText("");
                    }
                }else {
                    pinnuevo1.setText("");
                    pinnuevo2.setText("");
                    Toast.makeText(Configuracion.this, "Los campos no deben quedar vacios para continuar, intente nuevamente",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
