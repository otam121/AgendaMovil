package com.example.juan.practicas;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import objetos.Cliente;

/**
 * Created by JuanSalinas on 06/09/2017.
 */

public class ClienteSelectAbono extends Fragment {

    //DECLARANDO LAS VARIABLES PARA NUESTROS TEXVIEWS DE NUESTRO XML
    private TextView NOMBRE, DEUDA, ABONO, FECHA_ABONO;
    private Button ABONAR;
    private EditText CANTIDAD_ABONO;
    //VARIABLES NECESARIAS PARA ALGUNAS OPERACIONES
    String DEUDA_C, nombre;
    int NVO_DEUDA, NVO_ABONO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cliente_seleccionado, container, false);

        //ASOCIANDO NUESTROS OBJETOS CON SUS RESPECTIVOS ID DEL XML
        NOMBRE = (TextView)rootView.findViewById(R.id.lblnombredelcliente);
        DEUDA = (TextView)rootView.findViewById(R.id.lbldeudadelcliente);
        ABONO = (TextView)rootView.findViewById(R.id.lblUltimoabono);
        FECHA_ABONO =(TextView)rootView.findViewById(R.id.lblfecha);

        //EDITEXT CANTIDAD A ABONAR
        CANTIDAD_ABONO = (EditText)rootView.findViewById(R.id.txtnuevoabono);

        //BOTON ABONAR
        ABONAR = (Button)rootView.findViewById(R.id.btnrealizarabono);

        //RECUPERANDO EL BUNDLE ENVIADO DEL ACTIVITI PASADO
        Bundle bundle = getActivity().getIntent().getExtras();
        //ASISGNANDO EL VALOR DEL BUNDLE EN LA VARIABLE NOMBRE
        nombre = bundle.getString("NOMBRE");
        NOMBRE.setText(nombre);

        //ARRAY MESES DEL AÑO
        final String [] mesesdelano = new String[12];
        mesesdelano[0]="Ene";
        mesesdelano[1]="Feb";
        mesesdelano[2]="Mar";
        mesesdelano[3]="Abr";
        mesesdelano[4]="May";
        mesesdelano[5]="Jun";
        mesesdelano[6]="Jul";
        mesesdelano[7]="Ago";
        mesesdelano[8]="Sep";
        mesesdelano[9]="Oct";
        mesesdelano[10]="Nov";
        mesesdelano[11]="Dic";

        //OBTENIENDO LOS DATOS DEL CLIENTE SELECCIONADO DE LA LISTA
        MenuOpciones.REFERENCIACLIENTE.child(nombre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CREANDO OBJETO CLIENTE DE LA CLASE CLIENTE LA CUAL CONTENDRA LOS DATOS DEL SNAPSHOT
                Cliente cliente = dataSnapshot.getValue(Cliente.class);

                DEUDA_C = cliente.getAdeudo();

                DEUDA.setText("$"+cliente.getAdeudo());
                ABONO.setText("$"+cliente.getUltimoPago());
                FECHA_ABONO.setText(cliente.getFechaPago());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //METODO ONCLICK DEL BOTON ABONAR
        ABONAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CANTIDAD_ABONO.getText().length() != 0){

                    AlertDialog.Builder ventanarealizarabono = new AlertDialog.Builder(getContext());
                    ventanarealizarabono.setTitle("NUEVO ABONO!");
                    ventanarealizarabono.setMessage("\nSe ralizara un abono con la cantidad de: $"+CANTIDAD_ABONO.getText().toString()+"\n¿Desea continuar con la operacion?" );

                    //CREANDO UN OBJETO DE TIPO EDITEXT
                    final EditText pin = new EditText(getContext());
                    //APLICANDO ALGUNAS PROPIEDADES A NUESTRO EDITEX CREADO
                    pin.setGravity(Gravity.CENTER);
                    pin.setInputType(InputType.TYPE_CLASS_NUMBER);
                    pin.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    //AGREGANDO NUESTO EDITEXT A NUESTRA VENTANA ALERTDIALOG
                    ventanarealizarabono.setView(pin);

                    //METODO DEL BOTON POSITIVO DEL NUESTRO ALERTDIALOG
                    ventanarealizarabono.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //OBTENIENDO LA CANTIDAD DEL ABONO RELIZADO
                            NVO_ABONO = Integer.parseInt(CANTIDAD_ABONO.getText().toString());
                            //CONVIRTIENDO DEUDA_C A ENTERO
                            NVO_DEUDA = Integer.parseInt(DEUDA_C);
                            NVO_DEUDA= NVO_DEUDA-NVO_ABONO;


                            //OBTENIEDNO LA FECHA
                            Calendar fecha = Calendar.getInstance();
                            int ano = fecha.get(Calendar.YEAR);
                            int mes = fecha.get(Calendar.MONTH);
                            int dia = fecha.get(Calendar.DAY_OF_MONTH);

                            String HOY = dia+"/"+mesesdelano[mes]+"/"+ano;
                            String deudasting = Integer.toString(NVO_DEUDA);
                            String abonostring = Integer.toString(NVO_ABONO);

                            MenuOpciones.REFERENCIACLIENTE.child(nombre).child("adeudo").setValue(deudasting);
                            MenuOpciones.REFERENCIACLIENTE.child(nombre).child("ultimoPago").setValue(abonostring);
                            MenuOpciones.REFERENCIACLIENTE.child(nombre).child("fechaPago").setValue(HOY);

                            CANTIDAD_ABONO.setText("");
                            Toast.makeText(getContext(),"Abono Realizado",Toast.LENGTH_SHORT).show();

                        }
                    });

                    ventanarealizarabono.show();

                }else {
                    Toast.makeText(getContext(), "Debe de Introducir la cantidad del Abono para relizar esta operacion",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

        return rootView;
    }
}
