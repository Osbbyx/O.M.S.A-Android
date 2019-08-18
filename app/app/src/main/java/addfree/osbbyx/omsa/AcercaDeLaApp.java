package addfree.osbbyx.omsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AcercaDeLaApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de_la_app);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }
}
