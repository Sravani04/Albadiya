package app.mamac.albadiya;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 30-12-2016.
 */

public class ForgotPasswordActivity extends Activity {
    EditText email;
    TextView cancel_btn;
    TextView done_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);
        email =  (EditText) findViewById(R.id.email);
        cancel_btn = (TextView) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordActivity.this.onBackPressed();
            }
        });
        done_btn = (TextView) findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string =  email.getText().toString();
                if (email_string.equals("")){
                    Toast.makeText(ForgotPasswordActivity.this,"please enter email",Toast.LENGTH_SHORT).show();
                }else {
                    Ion.with(ForgotPasswordActivity.this)
                            .load(Settings.SERVER_URL+"forget-password.php")
                            .setBodyParameter("email",email_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(ForgotPasswordActivity.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        ForgotPasswordActivity.this.onBackPressed();
                                    }else {
                                        Toast.makeText(ForgotPasswordActivity.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }
}
