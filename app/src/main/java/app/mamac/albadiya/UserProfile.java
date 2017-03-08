package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by T on 03-12-2016.
 */

public class UserProfile extends Fragment{
    FrameLayout fragment_one;
    TextView login_btn;
    TextView signup_btn;
    @Override
    public View onCreateView(final LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.user_profile,container,false);
        fragment_one = (FrameLayout) view.findViewById(R.id.fragment_one);
        login_btn = (TextView) view.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstaLogin instaLogin = new InstaLogin();
                getFragmentManager().beginTransaction().replace(R.id.fragment_one,instaLogin).commit();
            }
        });

        signup_btn = (TextView) view.findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstaRegister instaRegister = new InstaRegister();
                getFragmentManager().beginTransaction().replace(R.id.fragment_one,instaRegister).commit();
            }
        });

       LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.edit_profile);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
