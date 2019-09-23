package addfree.osbbyx.omsa;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServicioObjetoExtraviados extends AppCompatActivity {
    //SERVICIO 1 RECLAMACION DE OBJETOS
    Spinner sp1;
    Button btn;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser origin = auth.getCurrentUser();
    private DatabaseReference Clases;
    EditText t1,t2,t3,t4,t5,t6,t3m,t7;
    private int dia,mes,ano,hora,minutos;
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
        setContentView(R.layout.activity_servicio_objeto_extraviados);

        //---------------el alertdiag ----------------------

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(R.string.P1_contenidoObjetosPerdidos).setIcon(R.mipmap.ic_omsa).setPositiveButton(R.string.entendido, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog titulo = alerta.create();
        titulo.setTitle(R.string.menu_1);
        titulo.show();

        //---------------------------------------------------

        Clases = FirebaseDatabase.getInstance().getReference("usuarios");

        sp1= (Spinner)findViewById(R.id.sp1);

        String [] opciones = {"Corredor 27 de Febrero","Corredor Kennedy","Corredor Alcarrizos", "Corredor Maximo Gomez", "Corredor Independencia",
        "Corredor Los Rios", "Corredor Naco", "Corredor Charles de Gaulle", "Corredor La Barquita", "Corredor Juan Bosch", "Corredor Lincoln",
        "Corredor Olimpico","Corredor Luperon","Corredor Bolivar","Corredor Central(Santiago)", "Corredor Canabacoa(Santiago)","Corredor Gurabo(Santiago)"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        sp1.setAdapter(adapter);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btn = (Button)findViewById(R.id.btn);

        //nuevo
        context = this;


        t1 = (EditText)findViewById(R.id.et1);
        t2 = (EditText)findViewById(R.id.et2);
        t3 = (EditText)findViewById(R.id.et3);
        t3m = (EditText)findViewById(R.id.et3m);
        t4 = (EditText)findViewById(R.id.et4);
        t5 = (EditText)findViewById(R.id.et5);
        t6 = (EditText)findViewById(R.id.et6);
        t7 = (EditText)findViewById(R.id.momento);

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
    public void seleccion(View view){
        String s1 = sp1.getSelectedItem().toString();
    }


//-------------------------------------------------------------------------------------------------------------------------
    //nuevo

    public void onClick(View v) {
        t7.setText(currentTime.toString());

        //---------------------CONDICIONES-----------------------------------------------------------------------------------------------------------------

        if(TextUtils.isEmpty(t4.getText())) {
            Toast.makeText(this, "Se debe ingresar la fecha de perdida", Toast.LENGTH_LONG).show();
            return;
        }
        else  if(TextUtils.isEmpty(t5.getText())) {
            Toast.makeText(this, "Se debe ingresar una hora aproximada", Toast.LENGTH_LONG).show();
            return;
        }else  if(TextUtils.isEmpty(t6.getText())) {
            Toast.makeText(this, "Se debe ingresar una descripcion", Toast.LENGTH_LONG).show();
            return;
        }else{
            // mensajes al email----------------------------------------------------------------------------------------------------------------------------------------------------
            reciep.setText(origin.getEmail());
            rec = reciep.getText().toString();
            sub.setText("OMSA");
            subject = sub.getText().toString();
            msg.setText("Se a hecho la reclamacion con exito!! señor(a) "+t1.getText()+ ", Gracias por utilizar nuestros servicios." +
                    "<br>"+"<br>"+"Este es un mensaje de confirmacion (No responder a este mensaje)");
            textMessage = msg.getText().toString();
            reciep1.setText("omsaapp@gmail.com");
            rec1 = reciep1.getText().toString();
            sub1.setText("Reclamacion de objetos extraviados. (OMSA APP)");
            subject1 = sub1.getText().toString();
            msg1.setText("Se a hecho una reclamacion con los siguientes datos: "+"<br>"+"<br>"+"Nombre: "+t1.getText()+"<br>"+"Telefono: "+t2.getText()+"<br>"+"Email: "+t3.getText()
                    +"<br>"+"Ficha de autobus: "+t3m.getText()+"<br>"+"Fecha de la perdida: "+t4.getText()+"<br>"+"Hora aproximada: "+t5.getText()+"<br>"+"Corredor: "+sp1.getSelectedItem().toString()+
                    "<br>"+"Descripcion del objeto: "+t6.getText()+"<br>"+"<br>"+"Esta reclamacion fue realizada el ("+t7.getText()+") Mediante la App mobile de la OMSA");
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
//------------------------------------------------------------------------------------------------------------------------------------
        }

        //-------------------------------------------------------------------------------------------------------------------------------------------------



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

            Intent home = new Intent(ServicioObjetoExtraviados.this, MainActivity.class);
            startActivity(home);
            finish();
        }
    }



    public void fecha(View view){
        final Calendar c= Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                t4.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }
        ,ano,mes,dia);
        datePickerDialog.show();
    }

    public void hora(View view){
        final Calendar c= Calendar.getInstance();
        hora=c.get(Calendar.HOUR_OF_DAY);
        minutos=c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                t5.setText(hourOfDay+":"+minute);
            }
        },hora
         ,minutos,false      );
        timePickerDialog.show();
    }
}

