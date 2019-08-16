package addfree.osbbyx.omsa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class servicioObjetosExtraviados extends AppCompatActivity {

    //SERVICIO 2 SERVICIO DE AUTOBUSES
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser origin = auth.getCurrentUser();
    private DatabaseReference Clases;

    EditText t1,t2,t3,t4,t5,t6;

    //nuevo
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep, sub, msg, reciep1, sub1, msg1;
    String rec, subject, textMessage, rec1, subject1, textMessage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_objetos_extraviados);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        Clases = FirebaseDatabase.getInstance().getReference("usuarios");

        t2 = (EditText)findViewById(R.id.et2);
        t4 = (EditText)findViewById(R.id.et4);
        t5 = (EditText)findViewById(R.id.et5);


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



                            Log.e("NombreUsuario", "" + nombre);
                            Log.e("TelefonoUsuario", "" + telefono);
                            Log.e("edadUsuario", "" + edad);
                            Log.e("emaillUsuario", "" + emaill);

                            Log.e("Datos:", "" + snapshot.getValue());

                            if (emaill.equals(origin.getEmail())) {
                              t2.setText(telefono);
                                t4.setText(nombre);
                                t5.setText(origin.getEmail());
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


        //nuevo
        context = this;

        reciep = (EditText) findViewById(R.id.ev1);
        sub = (EditText) findViewById(R.id.ev2);
        msg = (EditText) findViewById(R.id.ev3);
        reciep1 = (EditText) findViewById(R.id.ev5);
        sub1 = (EditText) findViewById(R.id.ev5);
        msg1 = (EditText) findViewById(R.id.ev6);

        /* login.setOnClickListener(this);*/


    }





//nuevo
    public void onClik(View v) {
        reciep.setText(origin.getEmail());
        rec = reciep.getText().toString();
        sub.setText("OMSA");
        subject = sub.getText().toString();
        msg.setText("Se a hecho la solicitud con exito, Gracias por utilizar nuestros servicios.(No responder a este mensaje)");
        textMessage = msg.getText().toString();
        reciep1.setText("omsaapp@gmail.com");
        rec1 = reciep1.getText().toString();
        sub1.setText("Solicitud de servicio especial de autobuses. (OMSA APP)");
        subject1 = sub1.getText().toString();
        msg1.setText("todo");
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

        RetreiveFeedTask task = new RetreiveFeedTask();
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
           /* pdialog.dismiss();
            reciep.setText("");
            msg.setText("");
            sub.setText("");*/
            Toast.makeText(getApplicationContext(), "A concluido su peticion con exito!", Toast.LENGTH_LONG).show();

            Intent home = new Intent(servicioObjetosExtraviados.this, MainActivity.class);
            startActivity(home);
            finish();
        }
    }


}
