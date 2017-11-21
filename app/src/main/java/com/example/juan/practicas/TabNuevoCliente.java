package com.example.juan.practicas;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by JuanSalinas on 02/09/2017.
 */

public class TabNuevoCliente extends Fragment {

    //variables
    String dateA, dateC, nombrenuevocliente, deuda, uabono, ucargo;


    //Declaracion de los objetos java que representaran nuestros objetos xml
    //Aqui todos los Editext
    private EditText nombrecliente;
    private EditText deudaactual;
    private EditText ultimoabono;

    private EditText ultimocargo;


    //Aqui todos los TextView
    private TextView fechaultimoabono;
    private TextView fechaultimocargo;
    //Aqui todos los Butoons
    private Button agregarcliente;
    //Aqui los datapikers
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener nDataSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nuevocliente, container, false);

        //Asociando nuestras variables con nuestros objetos xml a travez de su id
        //Editext
        nombrecliente = (EditText) rootView.findViewById(R.id.txtnombreCliente);
        deudaactual = (EditText) rootView.findViewById(R.id.txtAdeudo);
        ultimoabono = (EditText) rootView.findViewById(R.id.txtUltimoabono);
        ultimocargo = (EditText) rootView.findViewById(R.id.txtUltimocargo);

        //TextViews
        fechaultimoabono= (TextView) rootView.findViewById(R.id.txtFechaUltimoabono);
        fechaultimocargo = (TextView) rootView.findViewById(R.id.txtFechaUltimocargo);
        //Buttons
        agregarcliente = (Button) rootView.findViewById(R.id.btnagregarcliente);

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

        //Seleccionar la fecha del ultimo abono presionando el Editext
        fechaultimoabono.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        ano,mes,dia);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateA =dayOfMonth+"/"+mesesdelano[month]+"/"+year;
                fechaultimoabono.setText(dateA);
            }
        };

        //Seleccionar la fecha del ultimo cargo por pedido al presionar el TextView
        fechaultimocargo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        nDataSetListener,
                        ano,mes,dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        nDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateC = dayOfMonth+"/"+mesesdelano[month]+"/"+year;
                fechaultimocargo.setText(dateC);

            }
        };

        //------------------------------------------------BOTON AGREGAR CLIENTE--------------------------------------------------
        agregarcliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //OBTENIENDO VALORES DE NUESTROS EDITEX Y ASIGNANDOLOS A NUESTRAS VARIABLES
                nombrenuevocliente = nombrecliente.getText().toString();
                deuda = deudaactual.getText().toString();
                uabono = ultimoabono.getText().toString();
                ucargo = ultimocargo.getText().toString();



                //CONTROL PARA EL MOMENTO DE REGISTRAR NUEVO CLIENTE
                if (nombrenuevocliente.length() != 0 && deuda.length() != 0 && uabono.length() != 0 &&
                        ucargo.length() != 0 && fechaultimoabono.getText().length() != 0 && fechaultimocargo.getText().length() != 0){

                    AlertDialog.Builder ventananuevocliente = new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    ventananuevocliente.setMessage("\nSe creara un nuevo cliente con el nombre de:"+nombrecliente.getText().toString()+ "\n¿Está seguro de continuar?");
                    ventananuevocliente.setTitle("NUEVO CLIENTE");

                    final EditText txtpincliente = new EditText(getContext());

                    txtpincliente.setWidth(100);
                    txtpincliente.setHint("Escriba su PIN");
                    //CENTRAR
                    txtpincliente.setGravity(Gravity.CENTER);
                    //SELECCIONAR EL INPUTTYPE PARA NUESTO EDITEXT
                    txtpincliente.setInputType(InputType.TYPE_CLASS_NUMBER);
                    //OCULTA EL CONTENIDO DE NUESTRO EDITEXT COMO UNA CONTRASEÑA
                    txtpincliente.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    ventananuevocliente.setView(txtpincliente);

                    ventananuevocliente.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //VERIFICANDO PIN DEL USUARIO
                            SharedPreferences pref =getActivity().getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);
                            String pin = pref.getString("pin","1");
                            if (txtpincliente.getText().toString().equals(pin)){

                                //CARGANDO LOS VALORES A LA BASE DE DATOS
                                MenuOpciones.REFERENCIACLIENTE.child(nombrenuevocliente).child("adeudo").setValue(deuda);
                                MenuOpciones.REFERENCIACLIENTE.child(nombrenuevocliente).child("ultimoPago").setValue(uabono);
                                MenuOpciones.REFERENCIACLIENTE.child(nombrenuevocliente).child("fechaPago").setValue(dateA);
                                MenuOpciones.REFERENCIACLIENTE.child(nombrenuevocliente).child("ultimoCargo").setValue(ucargo);
                                MenuOpciones.REFERENCIACLIENTE.child(nombrenuevocliente).child("fechaCargo").setValue(dateC);

                                Toast.makeText(getContext(),"Cliente registrado exitosamente",Toast.LENGTH_SHORT).show();

                                //LIMPIANDO LOS TEXVIEW Y EDITEXT DEL FORMULARIO
                                nombrecliente.setText("");
                                deudaactual.setText("");
                                ultimoabono.setText("");
                                ultimocargo.setText("");
                                fechaultimoabono.setText("");
                                fechaultimocargo.setText("");

                                Intent refresh = new Intent(getContext(),MenuOpciones.class);
                                startActivity(refresh);

                            }else {
                                Toast.makeText(getContext(),"ERROR, PIN incorrecto",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    ventananuevocliente.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ventananuevocliente.show();

                }else
                    Toast.makeText(getContext(),"No debe dejar ningun campo en blanco para agregar un cliente nuevo",Toast.LENGTH_SHORT).show();

            }
        });


        return rootView;
    }

}
