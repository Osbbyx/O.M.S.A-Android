package addfree.osbbyx.omsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class aNoticias extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_noticias);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        wv = (WebView)findViewById(R.id.wv1);

        String URL = "http://omsa.gob.do/index.php/noticias";
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(URL);
    }
}
