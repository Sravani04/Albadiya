package app.mamac.albadiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by T on 30-12-2016.
 */

public class SettingsFragment extends Fragment {
    TextView change_password;
    ImageView back_btn;
    TextView edit_profile;
    TextView log_out;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.settings_fragment,container,false);
        change_password = (TextView) view.findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(),ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        back_btn = (ImageView)  view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        edit_profile = (TextView) view.findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile editProfile = new EditProfile();
                getFragmentManager().beginTransaction().replace(R.id.fragment,editProfile).commit();
            }
        });

        log_out = (TextView) view.findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.SetUserId(getActivity(),"-1");
                getActivity().onBackPressed();
            }
        });


        return view;
    }
}
