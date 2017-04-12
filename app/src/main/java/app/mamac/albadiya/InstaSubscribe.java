package app.mamac.albadiya;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 20-12-2016.
 */

public class InstaSubscribe extends Fragment {
    private WebView wv1;
    String amount="200";
    String member_id;
    ProgressBar progress;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.subscribe_page,container,false);
        wv1=(WebView) view.findViewById(R.id.webView);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.addJavascriptInterface(new WebAppInterface(getContext()),"app");
        wv1.setWebViewClient(new WebViewClient());
        wv1.setWebChromeClient(new MyWebViewClient());
        wv1.loadUrl(Settings.PAYMENT_URL + "member_id=" + Settings.GetUserId(getContext()) + "&amount=" + amount);
       // wv1.loadUrl(Settings.PAY_URL + "amount=" + amount);
       // Log.e("pay_url",Settings.PAY_URL + "amount=" +amount);
        Log.e("payment_url",Settings.PAYMENT_URL + "member_id=" + Settings.GetUserId(getContext()) + "&amount=" + amount);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        progress.setMax(100);
        progress.setProgress(0);
        return view;

    }


    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void send_message(String toast,Boolean success) {
            Log.e("toast",toast);
            if(toast.equals("success")){
                Ion.with(getContext())
                        .load("http://naqshapp.com/albadiya/api/payment.php")
                        .setBodyParameter("member_id", Settings.GetUserId(getContext()))
                        .setBodyParameter("result", "SUCCESS")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                try {
                                    Log.e("payment_response",result.toString());
                                    if (result.get("status").getAsString().equals("Success")) {
                                        Toast.makeText(getContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                    } else {
                                        Toast.makeText(getContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e1){
                                    e1.printStackTrace();
                                }

                            }
                        });
            }

            else{
                Log.e("tost",toast);
                Ion.with(getContext())
                        .load("http://naqshapp.com/albadiya/api/payment.php")
                        .setBodyParameter("member_id", Settings.GetUserId(getContext()))
                        .setBodyParameter("result", "FAILURE")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                try {
                                    if (result.get("status").getAsString().equals("Success")) {
                                        Toast.makeText(getContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e1){
                                    e1.printStackTrace();
                                }

                            }
                        });
            }
        }
    }


    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            InstaSubscribe.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);

        }
    }




    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }


}
