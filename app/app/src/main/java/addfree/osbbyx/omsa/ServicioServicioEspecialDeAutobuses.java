package addfree.osbbyx.omsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ServicioServicioEspecialDeAutobuses extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_servicio_especial_de_autobuses);

        wv = (WebView)findViewById(R.id.wv1);

        String URL = "https://docs.google.com/spreadsheets/d/1AIxENVfZLWqCFaqsGZsZe_rRMIfZbeI5Fo4vyFUlbsU/edit?usp=sharing";
        wv.setWebChromeClient(new WebChromeClient());
        wv.loadUrl(URL);
    }

}
