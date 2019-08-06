package addfree.osbbyx.omsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ServicioObjetoExtraviados extends AppCompatActivity {

    Spinner sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_objeto_extraviados);

        sp1= (Spinner)findViewById(R.id.sp1);

        String [] opciones = {"Corredor 27 de Febrero","Corredor JF. Kennedy","Corredor Naco","Ruta Universitaria"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        sp1.setAdapter(adapter);

    }

    public void seleccion(View view){
        String s1 = sp1.getSelectedItem().toString();

    }
}
