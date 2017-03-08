package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
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
 * Created by T on 23-11-2016.
 */

public class ChangePassword extends Activity {
   EditText oldpw;
   EditText newpw;
   EditText cnfrmpw;
   TextView submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        oldpw = (EditText) findViewById(R.id.oldpw);
        newpw = (EditText) findViewById(R.id.newpw);
        cnfrmpw = (EditText) findViewById(R.id.cnfrmpw);
        submit_button = (TextView) findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpassword_string = oldpw.getText().toString();
                String newpassword_string = newpw.getText().toString();
                String cnfrmpw_string     = cnfrmpw.getText().toString();

                if (oldpassword_string.equals("")){
                    Toast.makeText(ChangePassword.this,"please enter old password",Toast.LENGTH_SHORT).show();
                }else if(newpassword_string.equals("")){
                    Toast.makeText(ChangePassword.this,"please enter new password",Toast.LENGTH_SHORT).show();
                }else if (cnfrmpw_string.equals("")){
                    Toast.makeText(ChangePassword.this,"Please confirm new password",Toast.LENGTH_SHORT).show();
                }else {
                    final ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
                    progressDialog.setMessage("password is updating");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(ChangePassword.this)
                            .load("http://clients.yellowsoft.in/lawyers/api/change-password.php")
                            .setBodyParameter("opassword",oldpassword_string)
                            .setBodyParameter("password",cnfrmpw_string)
                            .setBodyParameter("password",newpassword_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (progressDialog!=null)
                                        progressDialog.dismiss();
                                  if (result.get("status").getAsString().equals("Failed")) {
                                            Toast.makeText(ChangePassword.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                            Intent submit = new Intent(ChangePassword.this, LawyerSettings.class);
                                            startActivity(submit);
                                        } else {
                                            Toast.makeText(ChangePassword.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                        }


                                }
                            });


                }
            }
        });


    }
}
