package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;



/**
 * Created by T on 11-11-2016.
 */

public class WebviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_activity);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://www.facebook.com");

    }
}
