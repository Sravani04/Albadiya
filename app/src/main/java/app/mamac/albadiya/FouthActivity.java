package app.mamac.albadiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by T on 08-11-2016.
 */

public class FouthActivity extends AppCompatActivity {
    TextView forgot_btn;
    EditText forgotpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        forgot_btn = (TextView) findViewById(R.id.forgot_btn);
        forgot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forgotpw.getText().toString().equals("")){
                    forgotpw.setError("Please enter email address");
                    Toast.makeText(FouthActivity.this,"Please enter email address",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(FouthActivity.this,"Please check your email",Toast.LENGTH_SHORT).show();
                    Intent activity = new Intent(FouthActivity.this, MainActivity.class);
                    startActivity(activity);
                }
            }
        });

        forgotpw = (EditText) findViewById(R.id.forgotpw);
    }
}
