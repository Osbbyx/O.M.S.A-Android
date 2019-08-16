package addfree.osbbyx.omsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText name;
    private EditText pass;
    private EditText phone;
    private EditText gmail;
    private EditText old;
    private EditText pass2;
    private ProgressDialog progressDialog;
    private Button btnRegistrar;
    private String cont = "" ;
    private EditText admin;

    private FirebaseAuth firebaseAuth;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    private DatabaseReference Clases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

            Clases = FirebaseDatabase.getInstance().getReference("usuarios");

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        /*myRef.getDatabase().getReference(user.getEmail());*/

        //Referenciamos los views
        name = (EditText) findViewById(R.id.nombre);
        pass = (EditText) findViewById(R.id.contra);
        phone = (EditText) findViewById(R.id.telefono);
        gmail = (EditText) findViewById(R.id.email);
        old = (EditText) findViewById(R.id.edad);
        pass2 = (EditText) findViewById(R.id.contra2);
        btnRegistrar = (Button) findViewById(R.id.btnlogin);
        admin = (EditText) findViewById(R.id.admin);

        progressDialog = new ProgressDialog(this);
    }



    private void registrarUsuario(){


        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = gmail.getText().toString().trim();
        String password  = pass.getText().toString().trim();
        String nombre = name.getText().toString().trim();
        String telefono = phone.getText().toString().trim();
        String edad = old.getText().toString().trim();
        String contrasena2 = pass2.getText().toString().trim();
        int a = Integer.parseInt(edad);


        //Verificamos que las cajas de texto no esten vacías

        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"Se debe ingresar un nombre",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(telefono)){
                Toast.makeText(this,"Se debe ingresar un telefono",Toast.LENGTH_LONG).show();
                return;
        }else if(telefono.length() != 10){
            Toast.makeText(this,"Introduzca un telefono valido",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(edad)){
                Toast.makeText(this,"Se debe ingresar una edad",Toast.LENGTH_LONG).show();
                return;
        }else if(a < 18 || a >150){
            Toast.makeText(this,"Introduzca una edad valida",Toast.LENGTH_LONG).show();
            return;
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }else if(password.length() < 6){
            Toast.makeText(this,"La contraseña debe ser mayor de 6 digitos",Toast.LENGTH_LONG).show();
            return;
        } else if(contrasena2.equals(password)){
        }else{//713400
            Toast.makeText(this,"Las contraseña no son iguales",Toast.LENGTH_LONG).show();
            return;
        }

        //Registrando administracion

        registrarClase();

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this,"Se ha registrado el usuario con el email: "+ gmail.getText(),Toast.LENGTH_LONG).show();
                            cont = "activo";
                            if(cont == "activo"){
                                Intent regi = new Intent(Register.this, Login.class);
                                startActivity(regi);
                                finish();

                            }

                        }else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(Register.this,"Este Email ya esta registrado ",Toast.LENGTH_LONG).show();
                                }
                            Toast.makeText(Register.this,"No se pudo registrar el usuario, colocar un email valido",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    public void registrarClase(){
        String adss = "1";
        String nombre = name.getText().toString();
        String telefono = phone.getText().toString();
        String edad = old.getText().toString();
        String email = gmail.getText().toString();
        if(admin.getText().toString().equals("")){
        }else{
            String adm = admin.getText().toString();
            adss = adm;
        }

        String id = Clases.push().getKey();


        Classes clas = new Classes(id,email,nombre,telefono,edad,adss);
        Clases.child("Usuarios").child(id).setValue(clas);

    }

    public void registrar(View view){
        registrarUsuario();

        if(cont == "activo"){
            registrarClase();
            Intent regi = new Intent(Register.this, Login.class);
            startActivity(regi);
            finish();
        }
    }



    public void volver(View view){
        Intent volver = new Intent(Register.this, Login.class);
        startActivity(volver);
        finish();

    }
}
