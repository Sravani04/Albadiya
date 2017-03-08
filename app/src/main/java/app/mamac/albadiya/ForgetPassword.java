package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * Created by T on 21-11-2016.
 */

public class ForgetPassword extends Activity {
    TextView submit;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        email = (EditText) findViewById(R.id.email);
        submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = email.getText().toString();
                email_string = email_string.trim();

                if(email_string.equals("")){
                    Toast.makeText(ForgetPassword.this,"Please enter email",Toast.LENGTH_SHORT).show();
                }else{
                    Ion.with(ForgetPassword.this)
                            .load("http://clients.yellowsoft.in/lawyers/api/forget-password.php")
                            .setBodyParameter("email",email_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if(result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(ForgetPassword.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        Intent progress = new Intent(ForgetPassword.this,LawyerLogin.class);
                                        startActivity(progress);
                                    }
                                    else{
                                        Toast.makeText(ForgetPassword.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }

            }
        });

    }
}
