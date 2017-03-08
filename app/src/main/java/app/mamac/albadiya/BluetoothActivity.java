package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;



/**
 * Created by T on 10-11-2016.
 */

public class BluetoothActivity extends AppCompatActivity {
    Switch switch_btn;
    TextView blue;
    String on = "ON";
    String off = "OFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        switch_btn = (Switch) findViewById(R.id.switch_btn);
        blue   = (TextView) findViewById(R.id.blue);
        switch_btn.setChecked(true);
        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                      blue.setText("on");
                }else{
                    blue.setText("off");
                }
            }
        });

        if(switch_btn.isChecked()){
            blue.setText("on");
        }else{
            blue.setText("off");
        }
    }
}
