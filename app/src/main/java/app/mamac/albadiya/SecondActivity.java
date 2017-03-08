package app.mamac.albadiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by T on 08-11-2016.
 */

public class SecondActivity extends AppCompatActivity {
    TextView reg_btn;
    EditText username,password;
    EditText first,last,email,emailcheck,pass;
    RadioButton male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        reg_btn = (TextView) findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first.getText().toString().equals("")) {
                    first.setError("Please enter firstname");
                    Toast.makeText(SecondActivity.this,"please enter firstname",Toast.LENGTH_SHORT).show();

                }else if(last.getText().toString().equals("")){
                    last.setError("Please enter lastname");
                    Toast.makeText(SecondActivity.this,"please enter lastname",Toast.LENGTH_SHORT).show();

                }else if(email.getText().toString().equals("")){
                    email.setError("Please enter email");
                    Toast.makeText(SecondActivity.this,"please enter email",Toast.LENGTH_SHORT).show();

                }else if(emailcheck.getText().toString().equals("")){
                    emailcheck.setError("Please re-enter email");
                    Toast.makeText(SecondActivity.this,"please re-enter email",Toast.LENGTH_SHORT).show();

                }else if(pass.getText().toString().equals("")){
                    pass.setError("Please enter password");
                    Toast.makeText(SecondActivity.this,"please enter password",Toast.LENGTH_SHORT).show();

                }
                else if( !(male.isChecked() || female.isChecked())){
                    Toast.makeText(SecondActivity.this,"please select gender",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(SecondActivity.this,"Registered successfully!!",Toast.LENGTH_SHORT).show();
                    Intent seventh_activity = new Intent(SecondActivity.this, MainActivity.class);
                    startActivity(seventh_activity);
                }
            }
        });



        first   = (EditText) findViewById(R.id.first);
        last    = (EditText) findViewById(R.id.last);
        email   = (EditText) findViewById(R.id.email);
        emailcheck = (EditText) findViewById(R.id.emailcheck);
        pass    = (EditText) findViewById(R.id.pass);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);

    }


}
