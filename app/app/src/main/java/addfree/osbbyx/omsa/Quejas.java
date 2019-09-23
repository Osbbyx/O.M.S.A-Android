package addfree.osbbyx.omsa;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Quejas extends AppCompatActivity {

    Button btnc;
    ImageView img;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser origin = auth.getCurrentUser();
    private DatabaseReference Clases;
    EditText t1,t2,t3,t7,et1,et2;
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    //-------------------------------------------------------------------------------------------------------------------------

    //nuevo
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep, sub, msg,reciep1, sub1, msg1;
    String rec, subject, textMessage,rec1, subject1, textMessage1;
    Date currentTime = Calendar.getInstance().getTime();

    //-------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quejas);

        Clases = FirebaseDatabase.getInstance().getReference("usuarios");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //-------------------------permisos----------------------------------------


        btnc = (Button) findViewById(R.id.btn);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }



        //---------------el alertdiag ----------------------

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(R.string.P1_contenidoQuejasSugerencia).setIcon(R.mipmap.ic_omsa).setPositiveButton(R.string.entendido, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog titulo = alerta.create();
        titulo.setTitle(R.string.menu_5);
        titulo.show();

        //---------------------------------------------------

        //nuevo
        context = this;


        t1 = (EditText)findViewById(R.id.t1);
        t2 = (EditText)findViewById(R.id.t2);
        t3 = (EditText)findViewById(R.id.t3);
        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        t7 = (EditText)findViewById(R.id.t7);


        reciep = (EditText) findViewById(R.id.ev1);
        sub = (EditText) findViewById(R.id.ev2);
        msg = (EditText) findViewById(R.id.ev3);
        reciep1 = (EditText) findViewById(R.id.ev5);
        sub1 = (EditText) findViewById(R.id.ev5);
        msg1 = (EditText) findViewById(R.id.ev6);



        /* login.setOnClickListener(this);*/

//-------------------------------------------------------------------------------------------------------------------------
        Clases.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Clases.child("Usuarios").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //Datos a recibir

                            Consulta user = snapshot.getValue(Consulta.class);
                            String nombre = user.getNombre();
                            String telefono = user.getTelefono();
                            String edad = user.getEdad();
                            String emaill = user.getEmaill();


                            Log.e("NombreUsuario", "" + nombre);
                            Log.e("TelefonoUsuario", "" + telefono);
                            Log.e("edadUsuario", "" + edad);
                            Log.e("emaillUsuario",""+emaill);

                            Log.e("Datos:", "" + snapshot.getValue());

                            if (emaill.equals(origin.getEmail())) {
                                t1.setText(nombre);
                                t2.setText(telefono);
                                t3.setText(origin.getEmail());
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
    }


//-------------------------------------------------------------------------------------------------------------------------
    //nuevo
   //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
public void lol(View v){
    Toast.makeText(getApplicationContext(), "Pronto!", Toast.LENGTH_LONG).show();
}

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    public void onClick(View v) {
        t7.setText(currentTime.toString());


            // mensajes al email----------------------------------------------------------------------------------------------------------------------------------------------------
            reciep.setText(origin.getEmail());
            rec = reciep.getText().toString();
            sub.setText("OMSA");
            subject = sub.getText().toString();
            msg.setText("Se a hecho la quejas o sugerencias con exito!! señor(a) "+t1.getText()+ ", Gracias por utilizar nuestros servicios." +
                    "<br>"+"<br>"+"Este es un mensaje de confirmacion (No responder a este mensaje)");
            textMessage = msg.getText().toString();
            reciep1.setText("omsaapp@gmail.com");
            rec1 = reciep1.getText().toString();
            sub1.setText("Queja y Sugerencias. (OMSA APP)");
            subject1 = sub1.getText().toString();
            msg1.setText("Se a hecho las quejas o sugerencias la persona con los siguientes datos: "+"<br>"+"<br>"+"Nombre: "+t1.getText()+"<br>"+"Telefono: "+t2.getText()+"<br>"+"Email: "+t3.getText()
                    +"<br>"+"Sugerencias o Quejas: "+et1.getText()+"<br>"+"Ficha de autobus:"+et2.getText()+"<br>"+"Estas sugerencias y quejas fueron realizadas el ("+t7.getText()+") Mediante la App mobile de la OMSA");
            textMessage1 = msg1.getText().toString();

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("omsaapp@gmail.com", "omsaticontrasena123");
                }
            });

            pdialog = ProgressDialog.show(context, "", "Procesando peticion...", true);

            Quejas.RetreiveFeedTask task = new Quejas.RetreiveFeedTask();
            task.execute();


    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("testfrom354@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec1));
                message.setSubject(subject1);
                message.setContent(textMessage1, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("testfrom354@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "A concluido su peticion con exito!", Toast.LENGTH_LONG).show();

            Intent home = new Intent(Quejas.this, MainActivity.class);
            startActivity(home);
            finish();
        }
    }


}

