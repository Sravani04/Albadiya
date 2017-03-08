package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * Created by T on 21-11-2016.
 */

public class LawyerLoginduplicate extends Activity{
    TextView login_btn;

    EditText email;
    EditText password;
    ImageView web_image;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyers_login);
        web_image = (ImageView) findViewById(R.id.web_image);
        Ion.with(this)
                .load("https://www.yellowsoft.in/wp-content/uploads/2014/05/womtttan-ceo.png")
                .withBitmap()
                .placeholder(R.drawable.snapdeal)
                .error(R.drawable.amazon)
                .intoImageView(web_image);
        login_btn = (TextView) findViewById(R.id.login_btn);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                email_string = email_string.trim();

                if(email_string.equals("")){
                    Toast.makeText(LawyerLoginduplicate.this,"Please enter email",Toast.LENGTH_SHORT).show();
                }else if(password_string.equals("")){
                    Toast.makeText(LawyerLoginduplicate.this,"Please enter password",Toast.LENGTH_SHORT).show();
                }else{
                    Ion.with(LawyerLoginduplicate.this)
                            .load("http://clients.yellowsoft.in/lawyers/api/login.php")
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("password",password_string)
                            .setBodyParameter("type","1")
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    // do stuff with the result or error
                                    //{"status":"Failure","message":"Please Enter Your Type"}
                                    if(e!=null){
                                        e.printStackTrace();
                                    }
                                    else {
                                        if (result.get("status").getAsString().equals("Success")) {
                                            Toast.makeText(LawyerLoginduplicate.this, result.get("name").getAsString(), Toast.LENGTH_SHORT).show();
                                            Intent skip = new Intent(LawyerLoginduplicate.this, LawyerTimeline.class);
                                            startActivity(skip);

                                        } else {
                                            Toast.makeText(LawyerLoginduplicate.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }


            }
        });
    }

}
