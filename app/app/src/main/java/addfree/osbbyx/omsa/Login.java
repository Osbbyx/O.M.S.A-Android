package addfree.osbbyx.omsa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Login extends AppCompatActivity {

    private EditText TextEmail;
    private EditText TextPassword;
    private TextView btnRegistrar;
    private ProgressDialog progressDialog;
    private Button btnLogin;
    private String cont;
    private CheckBox checkbox;


    //Declaramos un objeto firebaseAuth



    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();


        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.nombre);
        TextPassword = (EditText) findViewById(R.id.contra);

        btnRegistrar = (TextView) findViewById(R.id.botonRegistrar);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        checkbox = (CheckBox) findViewById(R.id.checkBox);

        progressDialog = new ProgressDialog(this);

        SharedPreferences preferences = getSharedPreferences("datos",Context.MODE_PRIVATE);
        TextEmail.setText(preferences.getString("mail", ""));
        TextPassword.setText(preferences.getString("pass", ""));

        checkbox.setChecked(true);

    }

    //Metodo para recordar
    public void Guardar(View view){
        SharedPreferences preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("mail",TextEmail.getText().toString());
        editor.putString("pass",TextPassword.getText().toString());
        editor.commit();
    }


    //Metodo firebase loguear
    private void loguearUsuario(){
        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();



        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Iniciando Sesion...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(Login.this,"Bienvenido: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                            cont = "activo";
                            if(cont == "activo") {
                                Intent login = new Intent(Login.this, MainActivity.class);
                                startActivity(login);
                                finish();
                            }
                        }else{

                            Toast.makeText(Login.this,"Contrasena o email incorrecto, volver a intentar ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    //Metodo login
    public void login(View view){

        if(checkbox.isChecked()){
            Guardar(view);
        }else{
            TextEmail.setText("");
            TextPassword.setText("");

        }
        loguearUsuario();
        if(cont == "activo") {
            Intent login = new Intent(Login.this, MainActivity.class);
            startActivity(login);
            finish();
        }
    }

    //Metodo Register
    public void register(View view){
        Intent regi = new Intent(Login.this, Register.class);
        startActivity(regi);
    }
}
