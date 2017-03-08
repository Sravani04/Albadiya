package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by T on 07-12-2016.
 */

public class InstaRegister extends Fragment {
    EditText name,password,email;
    TextView reg_btn;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final  View view = inflater.inflate(R.layout.instaregister_fragment,container,false);
        name = (EditText) view.findViewById(R.id.name);
        password = (EditText) view.findViewById(R.id.password);
        email = (EditText) view.findViewById(R.id.email);
        reg_btn = (TextView) view.findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_string = name.getText().toString();
                String password_string = password.getText().toString();
                String email_string = email.getText().toString();
                if (name_string.equals("")) {
                    Toast.makeText(getActivity(), "please enter name..", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                } else if (password_string.equals("")) {
                    Toast.makeText(getActivity(), "please enter password..", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                } else if (email_string.equals("")) {
                    Toast.makeText(getActivity(), "please enter email..", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                } else {
                    Toast.makeText(getActivity(),"Clicked on Register button",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
