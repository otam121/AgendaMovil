package com.example.juan.practicas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import objetos.FirebaseReferenses;

public class MenuOpciones extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    final public static FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    //HACIENDO REFERENCIA A LA INSTANCIA CLIENTES DE NUESTRA BASE DE DATOS
    final public static DatabaseReference REFERENCIACLIENTE = DATABASE.getReference(FirebaseReferenses.CLIENTES_REFERENSES);
    final public static DatabaseReference REFERENCIACONFIG = DATABASE.getReference(FirebaseReferenses.CONFIGURACION);

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //CREANDO LAS PREFERENCIAS
        //LAS PREFERENCIAS ALMACENAN EL PIN DEL USUARIO
        final SharedPreferences pref = getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);
        String nvopin = pref.getString("pin","1");
        Toast.makeText(MenuOpciones.this,nvopin,Toast.LENGTH_SHORT).show();
        //SI EL PIN ES IGUAL A 1 LA APLICACION LANZARARA LA BIENVENIDA PARA QUE EL USUARIO DETERMINE SU PIN
        if (nvopin.equals("1")) {

            Intent intent = new Intent(MenuOpciones.this, Introduccion.class);
            startActivity(intent);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_config:
                        Intent intent = new Intent(MenuOpciones.this, Configuracion.class);
                        startActivity(intent);
                        break;
                    case R.id.action_dia:
                        Intent intent1 = new Intent(MenuOpciones.this, MyDia.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TabClientes tabclientes = new TabClientes();
                    return tabclientes;
                case 1:
                    TabNuevoCliente tabnewcliente = new TabNuevoCliente();
                    return tabnewcliente;
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CLIENTES";
                case 1:
                    return "NVO. CLIENTE";
            }
            return null;
        }
    }

    //--------------------------------------------------METODOS----------------------------------------------------//


}
