package app.mamac.albadiya;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 07-12-2016.
 */

public class RegisterActivity extends Activity {
    EditText name,password,email;
    TextView reg_btn;
    ImageView back_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        reg_btn = (TextView) findViewById(R.id.reg_btn);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.onBackPressed();
            }
        });
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_string = name.getText().toString();
                String password_string = password.getText().toString();
                String email_string = email.getText().toString();
                if (name_string.equals("")) {
                    Toast.makeText(RegisterActivity.this, "please enter name.", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                } else if (password_string.equals("")) {
                    Toast.makeText(RegisterActivity.this, "please enter password.", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                } else if (email_string.equals("")) {
                    Toast.makeText(RegisterActivity.this, "please enter email.", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("please wait..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(RegisterActivity.this)
                            .load(Settings.SERVER_URL+"add-member.php")
                            .setBodyParameter("name",name_string)
                            .setBodyParameter("password",password_string)
                            .setBodyParameter("email",email_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (progressDialog!=null)
                                        progressDialog.dismiss();
                                    if (result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(RegisterActivity.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(RegisterActivity.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });
    }
}
