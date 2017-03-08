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
 * Created by T on 30-12-2016.
 */

public class ChangePasswordActivity extends Activity {
    EditText cpwd;
    EditText npwd;
    EditText cnpwd;
    TextView done_btn;
    TextView cancel_btn;
    TextView forget_password;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword_activity);
        cpwd = (EditText) findViewById(R.id.cpwd);
        npwd = (EditText) findViewById(R.id.npwd);
        cnpwd = (EditText) findViewById(R.id.cnpwd);
        done_btn = (TextView) findViewById(R.id.done_btn);
        forget_password = (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        cancel_btn = (TextView) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordActivity.this.onBackPressed();
            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_password = cpwd.getText().toString();
                String new_password = npwd.getText().toString();
                String confirm_password = cnpwd.getText().toString();
                if (current_password.equals("")){
                    Toast.makeText(ChangePasswordActivity.this,"please enter current password",Toast.LENGTH_SHORT).show();
                }else if (new_password.equals("")){
                    Toast.makeText(ChangePasswordActivity.this,"please enter new password",Toast.LENGTH_SHORT).show();
                }else if (confirm_password.equals("")){
                    Toast.makeText(ChangePasswordActivity.this,"please confirm new password",Toast.LENGTH_SHORT).show();
                }else {
                    Ion.with(ChangePasswordActivity.this)
                            .load(Settings.SERVER_URL + "change-password.php")
                            .setBodyParameter("opassword",current_password)
                            .setBodyParameter("password",new_password)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(ChangePasswordActivity.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        ChangePasswordActivity.this.onBackPressed();
                                    }else {
                                        Toast.makeText(ChangePasswordActivity.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
        });

    }
}
