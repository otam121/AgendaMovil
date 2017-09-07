package com.example.juan.practicas;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by JuanSalinas on 02/09/2017.
 */

public class TabClientes extends Fragment {

    ListView listaclientes;

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clientes, container, false);

        listaclientes=(ListView)rootView.findViewById(R.id.listviewclientes);
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,list);
        listaclientes.setAdapter(adapter);


        MenuOpciones.REFERENCIACLIENTE.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getKey();
                list.add(value);
                adapter.notifyDataSetChanged();

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

        //------------------------------ IDENTIFICANDO EL CLIENTE SELECCIONADO ---------------------------------------------//

        listaclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String clienteseleccioonado = (String)listaclientes.getItemAtPosition(position);
                Toast.makeText(getContext(),clienteseleccioonado,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(),ClienteSeleccionado.class);
                startActivity(intent);
            }
        });

        return rootView;

    }
}
