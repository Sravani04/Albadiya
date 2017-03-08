package app.mamac.albadiya;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;




/**
 * Created by T on 10-11-2016.
 */

public class SeventhActivity extends AppCompatActivity {
    Switch switch1, switch2;
    TextView switch_btn, switch_btn2;
    String switchOn = "Switch is ON";
    String switchOff = "Switch is OFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh);
        switch1 = (Switch) findViewById(R.id.switch1);
        switch2 = (Switch) findViewById(R.id.switch2);
        switch_btn = (TextView) findViewById(R.id.switch_btn);
        switch_btn2 = (TextView) findViewById(R.id.switch_btn2);
        switch1.setChecked(true);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switch_btn.setText(switchOn);
                }else{
                    switch_btn.setText(switchOff);
                }
            }
        });

        if (switch1.isChecked()) {
            switch_btn.setText(switchOn);
        }else {
            switch_btn.setText(switchOff);
        }

        switch2.setChecked(false);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switch_btn2.setText(switchOn);
                }else{
                    switch_btn2.setText(switchOff);
                }
            }
        });

        if (switch2.isChecked()) {
            switch_btn2.setText(switchOn);
        }else{
            switch_btn2.setText(switchOff);
        }
    }


}
