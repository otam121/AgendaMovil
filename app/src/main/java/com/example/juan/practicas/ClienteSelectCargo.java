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

public class ClienteSelectCargo extends Fragment {


    //DECLARANDO NUESTROS OBJETOS TEXVIEW PARA NUESTROS OBJETOS DEL XML
    private TextView NOMBRE, DEUDA, CARGO, FECHA_CARGO;
    private Button REALIZAR_CARGO;
    private EditText CANTIDAD_CARGO;

    //VARIABLES NECESARIAS PARA RELIZAR LAS OPERACIONES
    String DEUDA_C, nombre;
    int NVO_DEUDA, NVO_CARGO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cliente_seleccargo, container, false);

        //ASOCIANDO OBJETOS A SUS RESPECTIVOS ID DEL XML
        NOMBRE =(TextView)rootView.findViewById(R.id.lblnombredelcliente2);
        DEUDA = (TextView)rootView.findViewById(R.id.lbldeudadelcliente2);
        CARGO = (TextView)rootView.findViewById(R.id.lblUltimoacargo);
        FECHA_CARGO = (TextView)rootView.findViewById(R.id.lblfecha2);

        //EDITEXT CANTIDAD DEL CARGO
        CANTIDAD_CARGO = (EditText)rootView.findViewById(R.id.txtnuevocargo);
        //BOTON REALIZAR NUEVO CARGO
        REALIZAR_CARGO = (Button)rootView.findViewById(R.id.btnrealizarcargo);

        //OBTENIENDO EL OBJETO BUNDLE ENVIADO DEL ACTIVITI ANTERIOS
        Bundle bundle = getActivity().getIntent().getExtras();
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

        //METODO PARA OBTENER LOS DATOS DEL CLIENTE SELECCIONADO
        //LOS DATOS SON RECOLECTADOS DE FIREBASE NUESTRA BASE DE DATOS EN TIEMPO REAL
         MenuOpciones.REFERENCIACLIENTE.child(nombre).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 Cliente cliente = dataSnapshot.getValue(Cliente.class);

                 DEUDA_C = cliente.getAdeudo();

                 DEUDA.setText("$"+cliente.getAdeudo());
                 CARGO.setText("$"+cliente.getUltimoCargo());
                 FECHA_CARGO.setText(cliente.getFechaCargo());
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

        //METODO PARA REALIZAR UN NUEVO CARGO POR PEDIDO
        REALIZAR_CARGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CANTIDAD_CARGO.getText().length() != 0){

                    AlertDialog.Builder ventananuevocargo = new AlertDialog.Builder(getContext());
                    ventananuevocargo.setTitle("CARGO POR PEDIDO");
                    ventananuevocargo.setMessage("Se realizara un cargo de: $"+CANTIDAD_CARGO.getText().toString()+"\n¿Desea continuar con la operacion?");

                    //CREANDO UN OBJETO DE TIPO EDITEXT PARA NUESTRO PIN
                    final EditText pin = new EditText(getContext());
                    //APLICANDO ALGUNAS PROPIEDADES A NUESTRO EDITEXT
                    pin.setGravity(Gravity.CENTER);
                    pin.setInputType(InputType.TYPE_CLASS_NUMBER);
                    pin.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    //AGREGANDO NUESTRO EDITEXT A NUESTRO ALERTDIALOG
                    ventananuevocargo.setView(pin);

                    //METODO DEL BOTON POSITIVO DEL ALERTDIALOG
                    ventananuevocargo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //OBTENIENDO LA CANTIDAD DEL CARGO A REALIZAR
                            NVO_CARGO = Integer.parseInt(CANTIDAD_CARGO.getText().toString());
                            //CONVIRTIENDO LA DEUDA ACTUAL A ENTERO
                            NVO_DEUDA = Integer.parseInt(DEUDA_C);
                            //ROBTENIENDO EL NUEVO VALOR DE LA DEUDA ACTUAL
                            NVO_DEUDA = NVO_DEUDA+NVO_CARGO;

                            //OBTENIENDO LA FECCHA DEL DIA DE HOY
                            Calendar fecha = Calendar.getInstance();
                            int ano = fecha.get(Calendar.YEAR);
                            int mes = fecha.get(Calendar.MONTH);
                            int dia = fecha.get(Calendar.DAY_OF_MONTH);

                            String HOY = dia+"/"+mesesdelano[mes]+"/"+ano;

                            //CONVIRTIENDO VALORES A STRING PARA ALMACENARLOS EN LA BASE DE DATOS
                            String deudastring = Integer.toString(NVO_DEUDA);
                            String cargostring = Integer.toString(NVO_CARGO);

                            //ENVIANDO LOS VALORES A NUESTRA BASE DE DATOS
                            MenuOpciones.REFERENCIACLIENTE.child(nombre).child("adeudo").setValue(deudastring);
                            MenuOpciones.REFERENCIACLIENTE.child(nombre).child("ultimoCargo").setValue(cargostring);
                            MenuOpciones.REFERENCIACLIENTE.child(nombre).child("fechaCargo").setValue(HOY);

                            CANTIDAD_CARGO.setText("");
                            Toast.makeText(getContext(),"Cargo Realizado",Toast.LENGTH_SHORT).show();


                        }
                    });

                    ventananuevocargo.show();

                }else {
                    Toast.makeText(getContext(),"Debe introducir el monto del cargo para realizar con esta operacion",
                            Toast.LENGTH_SHORT).show();;
                }
            }
        });

        return rootView;
    }
}
