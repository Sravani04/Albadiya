package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import java.util.ArrayList;

/**
 * Created by T on 15-11-2016.
 */

public class LawyerSignup extends Activity {
    TextView register_btn;

    EditText first_name;
    EditText last_name;
    EditText user_name;
    EditText pass;
    EditText email_add;
    EditText phone;
    TextView area_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyers_signup);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        register_btn = (TextView) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname_string = first_name.getText().toString();
                String lastname_string  = last_name.getText().toString();
                String username_string  = user_name.getText().toString();
                String password_string  = pass.getText().toString();
                String email_string     = email_add.getText().toString();
                String phone_string     = phone.getText().toString();
                 if(firstname_string.equals("")){
                    Toast.makeText(LawyerSignup.this,"Please enter firstname",Toast.LENGTH_SHORT).show();
                    first_name.setError("Please enter firstname");
                }else if(lastname_string.equals("")){
                    Toast.makeText(LawyerSignup.this,"Please enter lastname",Toast.LENGTH_SHORT).show();
                    last_name.setError("Please enter lastname");
                }else if(username_string.equals("")){
                    Toast.makeText(LawyerSignup.this,"Please enter username",Toast.LENGTH_SHORT).show();
                    user_name.setError("Please enter username");
                }else if(password_string.equals("")){
                    Toast.makeText(LawyerSignup.this,"Please enter password",Toast.LENGTH_SHORT).show();
                    pass.setError("Please enter password");
                }else if(email_string .equals("")){
                    Toast.makeText(LawyerSignup.this,"Please enter email address",Toast.LENGTH_SHORT).show();
                    email_add.setError("Please enter email address");
                }else if(phone_string.equals("")){
                    Toast.makeText(LawyerSignup.this,"Please enter phone number",Toast.LENGTH_SHORT).show();
                    phone.setError("Please enter phone number");
                }else {
                    Ion.with(LawyerSignup.this)
                            .load(Settings.SERVER_URL+"add-member.php")
                            .setBodyParameter("fname",firstname_string)
                            .setBodyParameter("lname",lastname_string)
                            .setBodyParameter("username",username_string)
                            .setBodyParameter("phone",phone_string)
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("password",password_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    //{"status":"Failed","message":"This Email Already Registered"}
                                    if(result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(LawyerSignup.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        Intent register = new Intent(LawyerSignup.this,LawyerTimeline.class);
                                        startActivity(register);
                                    }else {
                                        Toast.makeText(LawyerSignup.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

        first_name = (EditText) findViewById(R.id.first_name);
        last_name  = (EditText) findViewById(R.id.last_name);
        user_name  = (EditText) findViewById(R.id.user_name);
        pass       = (EditText) findViewById(R.id.pass);
        email_add  = (EditText) findViewById(R.id.email_add);
        phone      = (EditText) findViewById(R.id.phone);
        area_btn = (TextView) findViewById(R.id.area_btn);
        area_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // show_areas();
                get_areas();
            }
        });

    }
         ArrayList<String> items = new ArrayList<>();
        CharSequence[] items_names = {};

    private void show_areas(){
      //  final String[] items = {"Foo", "Bar", "Baz"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        builder.setItems(items_names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection

                area_btn.setText(items.get(item));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void get_areas(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait loading countries...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Ion.with(this)
                //https://restcountries.eu/rest/v1/all
                .load("http://clients.yellowsoft.in/lawyers/api/areas.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        // do stuff with the result or error
                        //{"status":"Failure","message":"Please Enter Your Type"}
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        items_names = new CharSequence[result.size()];

                        for(int i=0;i<result.size();i++){
                            JsonObject country = result.get(i).getAsJsonObject();

                            items.add(country.get("title").getAsString());

                            items_names[i] = country.get("title").getAsString();

                        }
                        show_areas();


                    }
                });


    }
}
