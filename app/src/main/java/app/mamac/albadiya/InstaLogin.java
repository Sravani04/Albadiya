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

public class InstaLogin extends Fragment {
    TextView ln_btn;
    EditText name,password;
    @Override
    public View onCreateView(final LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.instalogin,container,false);
        name = (EditText) view.findViewById(R.id.name);
        password = (EditText) view.findViewById(R.id.password);
        ln_btn = (TextView) view.findViewById(R.id.ln_btn);
        ln_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_string = name.getText().toString();
                String password_string =  password.getText().toString();
                if (name_string.equals("")){
                    Toast.makeText(getActivity(),"please enter name..",Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }else if (password_string.equals("")){
                    Toast.makeText(getActivity(),"Please enter password..",Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }else {
                    Toast.makeText(getActivity(),"Clicked on Login button",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
