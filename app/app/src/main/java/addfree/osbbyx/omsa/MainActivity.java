package addfree.osbbyx.omsa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser origin = auth.getCurrentUser();
    private DatabaseReference Clases;
    EditText t1 ;
    LinearLayout special;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Servicios de la OMSA", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        Clases = FirebaseDatabase.getInstance().getReference("usuarios");

        t1 = (EditText)findViewById(R.id.et1) ;
        special = (LinearLayout)findViewById(R.id.btnespecial);

        //-----------------------------------ACCESOS DE PERMISOS----------------------------------------------------------------

        // esto muestra el dialogo de permisos
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, /* Este codigo es para identificar tu request */ 1);

        //-------------------------------------------------------------------------------------------------------------------------
        Clases.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Clases.child("Usuarios").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //Datos a recibir

                            Consulta user = snapshot.getValue(Consulta.class);
                            String nombre = user.getNombre();
                            String telefono = user.getTelefono();
                            String edad = user.getEdad();
                            String emaill = user.getEmaill();
                            String adss = user.getAdss();


                            Log.e("NombreUsuario", "" + nombre);
                            Log.e("TelefonoUsuario", "" + telefono);
                            Log.e("edadUsuario", "" + edad);
                            Log.e("emaillUsuario", "" + emaill);
                            Log.e("adUsuario", "" + adss);
                            Log.e("Datos:", "" + snapshot.getValue());

                            if (emaill.equals(origin.getEmail())) {
                                t1.setText(adss);
                                String codigo = t1.getText().toString();
                                if(codigo.equals("713400")){
                                   // special.setVisibility(View.VISIBLE);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //-------------------------------------------------------------------------------------------------------------------------
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent servicio1 = new Intent(this,ServicioObjetoExtraviados.class);
            startActivity(servicio1);
        } else if (id == R.id.nav_gallery) {
            Intent servicio2 = new Intent(this,servicioObjetosExtraviados.class);
            startActivity(servicio2);
        } else if (id == R.id.nav_slideshow) {
            Intent servicio3 = new Intent(this,aNoticias.class);
            startActivity(servicio3);
        } else if (id == R.id.nav_parada){
            Intent servicio4 = new Intent(this,ubicacion.class);
            startActivity(servicio4);
        }else if (id == R.id.nav_queja){
            Intent servicio4 = new Intent(this,Quejas.class);
            startActivity(servicio4);
        }else if (id == R.id.nav_director){
            Intent servicio4 = new Intent(this,Director.class);
            startActivity(servicio4);
        }else if (id == R.id.nav_share) {
            Intent nosotros = new Intent(this,SobreNosotros.class);
            startActivity(nosotros);
        }else if (id == R.id.nav_contacto) {
            Intent contacto = new Intent(this,Contacto.class);
            startActivity(contacto);
        } else if (id == R.id.nav_send) {
            Intent app = new Intent(this,AcercaDeLaApp.class);
            startActivity(app);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void servicio1(View view){
        Intent ir = new Intent(this,ServicioObjetoExtraviados.class);
        startActivity(ir);
    }

    public void servicio2(View view){
        Intent ir = new Intent(this,servicioObjetosExtraviados.class);
        startActivity(ir);
    }

    public void servicio3(View view){
        Intent ir = new Intent(this,aNoticias.class);
        startActivity(ir);
    }
    public void servicio4(View view){
        Intent ir = new Intent(this,ubicacion.class);
        startActivity(ir);
    }

    public void servicio5(View view){
        Intent ir = new Intent(this,Quejas.class);
        startActivity(ir);
    }

    public void servicio6(View view){
        Intent ir = new Intent(this,Director.class);
        startActivity(ir);
    }
}
