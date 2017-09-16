package com.example.juan.practicas;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by JuanSalinas on 14/09/2017.
 */

public class IntroduccionPage2 extends Fragment {

    private Button miboton;
    private EditText pin1, pin2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro2, container, false);

        //ASOCIANDO OBJETOS JAVA A OBJETOS XML
        miboton =(Button)rootView.findViewById(R.id.btnregistrarpin);
        pin1 = (EditText)rootView.findViewById(R.id.edtnuevopin1);
        pin2 = (EditText)rootView.findViewById(R.id.edtnuevopin2);

        miboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //VERIFICANDO QUE PIN1 Y PIN2 NO ESTEN VACIOS
                if (pin1.getText().length() != 0 && pin2.getText().length() != 0){
                    //VERIFICANDO QUE LOS PINS INTRODUCIDOS COINCIDAS
                        if (pin1.getText().toString().equals(pin2.getText().toString())){

                            if (pin1.getText().length() >=4 && pin2.getText().length() >=4) {
                                SharedPreferences pref = getActivity().getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("pin", pin1.getText().toString());
                                editor.commit();

                                MenuOpciones.REFERENCIACONFIG.child("pin").setValue(pin1.getText().toString());

                                Intent intent = new Intent(getContext(), MenuOpciones.class);
                                startActivity(intent);
                            }else {
                                pin1.setText("");
                                pin2.setText("");
                                Toast.makeText(getContext(),"El pin debe contener almenos 4 digitos por su seguridad, intente nuevamente",Toast.LENGTH_LONG).show();
                            }

                        }else{
                            //LOS PINS NO COINCIDEN
                            pin1.setText("");
                            pin2.setText("");
                            Toast.makeText(getContext(),"Los valores del PIN no coinciden intente una vez mas",Toast.LENGTH_LONG).show();
                        }

                }else {
                    pin1.setText("");
                    pin2.setText("");
                    Toast.makeText(getContext(), "Los campos no deben quedar vacios para continuar, intente nuevamente",
                            Toast.LENGTH_LONG).show();
                }

            }
        });


        return rootView;
    }
}
