package addfree.osbbyx.omsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class pdfActivity extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        wv = (WebView)findViewById(R.id.wbb);

        String URL = "http://omsa.gob.do/index.php/corredores/mapas-corredores-santo-domingo";
        wv.setWebChromeClient(new WebChromeClient());
        wv.loadUrl(URL);
    }


}
