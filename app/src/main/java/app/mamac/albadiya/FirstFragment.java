package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * Created by T on 30-11-2016.
 */

public class FirstFragment extends Fragment {
    EditText email;
    EditText password;
    TextView submit_btn;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view =  inflater.inflate(R.layout.first_fragment,container,false);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                String Email = email.getText().toString();
                Email = Email.trim();
                if (Email.equals("")){
                    Toast.makeText(getActivity(),"please enter username",Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else if (pass.equals("")){
                    Toast.makeText(getActivity(),"please enter password",Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }else if (pass.length()>10){
                    Toast.makeText(getActivity(),"password accepts only 10 characters",Toast.LENGTH_SHORT).show();
                }
                else{
                    Ion.with(FirstFragment.this)
                            .load("http://clients.yellowsoft.in/lawyers/api/login.php")
                            .setBodyParameter("email",Email)
                            .setBodyParameter("password",pass)
                            .setBodyParameter("type","1")
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")) {
                                        Toast.makeText(getActivity(),result.get("name").getAsString(), Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getActivity(),result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
            }
        });
        return view;
    }
}
