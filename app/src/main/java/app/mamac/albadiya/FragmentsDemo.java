package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;



/**
 * Created by T on 30-11-2016.
 */

public class FragmentsDemo extends FragmentActivity {
    FrameLayout fragment_one;
    TextView first_btn,sec_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentdemo_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        fragment_one = (FrameLayout) findViewById(R.id.first_fragment);
        first_btn = (TextView) findViewById(R.id.first_btn);
        sec_btn   = (TextView) findViewById(R.id.sec_btn);

        FirstFragment firstFragment = new FirstFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.first_fragment, firstFragment).commit();


        first_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstFragment firstFragment = new FirstFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.first_fragment, firstFragment).commit();

            }
        });

        sec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SecondFragment secondFragment = new SecondFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.first_fragment, secondFragment).commit();
            }
        });

    }
}